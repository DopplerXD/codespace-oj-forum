package site.dopplerxd.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import site.dopplerxd.backend.model.entity.Problem;
import site.dopplerxd.backend.service.ProblemService;
import site.dopplerxd.backend.mapper.ProblemMapper;
import org.springframework.stereotype.Service;

/**
 * @author doppleryxc
 * @description 针对表【problem】的数据库操作Service实现
 * @createDate 2025-02-20 17:53:04
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem>
        implements ProblemService {

}




