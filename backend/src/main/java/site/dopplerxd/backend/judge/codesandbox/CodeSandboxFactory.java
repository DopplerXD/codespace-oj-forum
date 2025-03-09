package site.dopplerxd.backend.judge.codesandbox;

import site.dopplerxd.backend.judge.codesandbox.impl.ExampleCodeSandbox;
import site.dopplerxd.backend.judge.codesandbox.impl.RemoteCodeSandbox;
import site.dopplerxd.backend.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂（根据类型创建代码沙箱）
 *
 * @author: <a href="https://github.com/DopplerXD">doppleryxc</a>
 * @time: 2025/3/9 14:51
 */
public class CodeSandboxFactory {

    /**
     * 创建代码沙箱示例
     *
     * @param type 沙箱类型
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        return switch (type) {
            case "example" -> new ExampleCodeSandbox();
            case "remote" -> new RemoteCodeSandbox();
            case "thirdParty" -> new ThirdPartyCodeSandbox();
            default -> new ExampleCodeSandbox();
        };
    }
}
