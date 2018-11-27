package support.lfp.ma.base;

/**
 * <pre>
 * Tip:
 *      一个模块化平台类。自定义模块可以使用平台相关的功能，而无需在平台上实现任何代码。
 *
 * Function:
 *
 * Created by LiFuPing on 2018/11/13 11:03
 * </pre>
 */
 interface ModulePlatformOwner extends ModulePlatformContext, ModulePlatformApi {

    /**
     * 获得当前模块化平台
     *
     * @return The module platform
     */
    ModulePlatform getPlatform();
}
