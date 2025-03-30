package site.dopplerxd.backend.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import site.dopplerxd.backend.common.ErrorCode;
import site.dopplerxd.backend.exception.BusinessException;
import site.dopplerxd.backend.model.dto.problem.ProblemQueryDto;
import site.dopplerxd.backend.model.entity.Judge;
import site.dopplerxd.backend.model.entity.Problem;
import site.dopplerxd.backend.model.vo.ProblemEditVO;
import site.dopplerxd.backend.model.vo.ProblemSummaryVO;
import site.dopplerxd.backend.model.vo.ProblemVO;
import site.dopplerxd.backend.service.JudgeService;
import site.dopplerxd.backend.service.ProblemService;
import site.dopplerxd.backend.mapper.ProblemMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author doppleryxc
 * @description 针对表【problem】的数据库操作Service实现
 * @createDate 2025-02-20 17:53:04
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem>
        implements ProblemService {

    @Resource
    private JudgeService judgeService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String PROBLEM_CACHE_KEY = "codespace:cache:problemvo:";

    @Override
    public ProblemVO getByPid(String pid) {
        if (pid == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProblemVO problemVO = getByPidWithMutex(pid);
        if (problemVO == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        return problemVO;
    }

    /**
     * 解决缓存击穿
     *
     * @param pid
     * @return
     */
    public ProblemVO getByPidWithMutex(String pid) {
        if (pid == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 修改为redis缓存查询
        // 1. 从redis查询problemVO
        String problemVOJson = stringRedisTemplate.opsForValue().get(PROBLEM_CACHE_KEY + pid);

        // 2. 如果Redis中存在，则直接返回
        if (StrUtil.isNotBlank(problemVOJson)) {
            return JSONUtil.toBean(problemVOJson, ProblemVO.class);
        }
        // 判断命中的是否为空值
        if (problemVOJson != null) {
            return null;
        }

        // 3. 实现缓存重建
        // 3.1 获取互斥锁
        String lockKey = "lock:problem:" + pid;
        ProblemVO problemVO = null;
        try {
            boolean isLock = tryLock(lockKey);
            if (!isLock) {
                // 3.2 失败则休眠并重试
                Thread.sleep(50);
                return getByPidWithMutex(pid);
            }
            // 3.3 成功，根据id查询数据库
            QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("problem_id", pid);
            Problem problem = this.getOne(queryWrapper);

            // 4. 如果数据库中也不存在，则缓存空值到redis中，并抛出异常
            if (problem == null) {
                stringRedisTemplate.opsForValue().set(PROBLEM_CACHE_KEY + pid, "", 2L, TimeUnit.MINUTES);
                return null;
            }

            // 5. 如果数据库中存在，则将查询结果存入redis，并设置超时时间
            problemVO = null;
            BeanUtils.copyProperties(problem, problemVO);
            stringRedisTemplate.opsForValue().set(PROBLEM_CACHE_KEY + pid, JSONUtil.toJsonStr(problemVO), 60L, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 6. 释放互斥锁
            unlock(lockKey);
        }

        // 7. 返回查询结果
        return problemVO;
    }

    /**
     * 解决缓存穿透
     *
     * @param pid
     * @return
     */
    public ProblemVO getByPidWithPassThrough(String pid) {
        if (pid == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 修改为redis缓存查询
        // 1. 从redis查询problemVO
        String problemVOJson = stringRedisTemplate.opsForValue().get(PROBLEM_CACHE_KEY + pid);
        ProblemVO problemVO = new ProblemVO();

        // 2. 如果Redis中存在，则直接返回
        if (StrUtil.isNotBlank(problemVOJson)) {
            problemVO = JSONUtil.toBean(problemVOJson, ProblemVO.class);
            return problemVO;
        }
        // 判断命中的是否为空值
        if (problemVOJson != null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }

        // 3. 如果redis中不存在，则从数据库查询
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id", pid);
        Problem problem = this.getOne(queryWrapper);

        // 4. 如果数据库中也不存在，则缓存空值到redis中，并抛出异常
        if (problem == null) {
            stringRedisTemplate.opsForValue().set(PROBLEM_CACHE_KEY + pid, "", 2L, TimeUnit.MINUTES);
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }

        // 5. 如果数据库中存在，则将查询结果存入redis，并设置超时时间
        BeanUtils.copyProperties(problem, problemVO);
        problemVOJson = JSONUtil.toJsonStr(problemVO);
        stringRedisTemplate.opsForValue().set(PROBLEM_CACHE_KEY + pid, problemVOJson, 60L, TimeUnit.MINUTES);

        // 6. 返回查询结果
        return problemVO;
    }

    /**
     * 尝试获取锁（缓存击穿）
     *
     * @param key
     * @return
     */
    private boolean tryLock(String key) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
//        return Boolean.TRUE.equals(flag);
    }

    /**
     * 释放锁
     *
     * @param key
     */
    private void unlock(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public ProblemEditVO getDetailByPid(String pid) {
        if (pid == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id", pid);
        Problem problem = this.getOne(queryWrapper);
        if (problem == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        ProblemEditVO problemEditVO = new ProblemEditVO();
        BeanUtils.copyProperties(problem, problemEditVO);
        // TODO: 将 json 字符串数组 转为 List<String>
        return problemEditVO;
    }

    @Override
    public JSONObject getProblemList(ProblemQueryDto queryDto, String userId) {
        int current = queryDto.getCurrent() > 0 ? queryDto.getCurrent() : 1; // 默认查第一页
        Integer difficulty = queryDto.getDifficulty();
        List<String> tags = queryDto.getTags();
        String keyword = queryDto.getKeyword();

        // 构建查询条件
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("problem_id");

        if (difficulty != null) {
            queryWrapper.eq("difficulty", difficulty);
        }
        if (tags!= null &&!tags.isEmpty()) {
            for (int i = 0; i < tags.size(); i++) {
                if (i > 0) {
                    queryWrapper.or();
                }
                queryWrapper.like("tags", tags.get(i));
            }
        }
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like("title", keyword).or().like("description", keyword);
        }

        IPage<Problem> problemPage = new Page<>(current, 30);
        IPage<Problem> pageResult = this.page(problemPage, queryWrapper);
        List<Problem> problems = pageResult.getRecords();
        List<ProblemSummaryVO> items = new LinkedList<>();

        for (Problem problem : problems) {
            ProblemSummaryVO problemSummaryVO = new ProblemSummaryVO();
            BeanUtils.copyProperties(problem, problemSummaryVO);

            // 判断提交状态
            if (userId == null) {
                problemSummaryVO.setStatus(0);
            } else {
                QueryWrapper<Judge> judgeQueryWrapper = new QueryWrapper<>();
                judgeQueryWrapper.eq("pid", problem.getProblemId());
                judgeQueryWrapper.eq("uid", userId);
                boolean exists = judgeService.exists(judgeQueryWrapper);
                if (exists) {
                    judgeQueryWrapper.eq("status", 0);
                    boolean isSolved = judgeService.exists(judgeQueryWrapper);
                    problemSummaryVO.setStatus(isSolved ? 1 : -1);
                } else {
                    problemSummaryVO.setStatus(0);
                }
            }

            items.add(problemSummaryVO);
        }

        JSONObject res = new JSONObject();
        res.set("problems", items);
        res.set("total", pageResult.getTotal());
        res.set("pages", pageResult.getPages());
        return res;
    }
}




