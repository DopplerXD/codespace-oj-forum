package site.dopplerxd.backend.model.enmus;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 代码语言枚举
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/2/21 16:46
 */
public enum JudgeSubmitLanguage {
    JAVA("java", "java"),
    C("c", "c"),
    CPP("cpp", "cpp"),
    PYTHON("python", "python"),
    PYPY("pypy", "pypy"),
    PYPY3("pypy3", "pypy3"),
    GOLANG("golang", "golang"),
    RUST("rust", "rust");


    private final String language;
    private final String value;

    JudgeSubmitLanguage(String language, String value) {
        this.language = language;
        this.value = value;
    }

    /**
     * 获取所有的 value 组成的列表
     *
     * @return List<String>
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return JudgeSubmitLanguage
     */
    public static JudgeSubmitLanguage getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeSubmitLanguage item : values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        return null;
    }

    public String getLanguage() {
        return language;
    }

    public String getValue() {
        return value;
    }
}
