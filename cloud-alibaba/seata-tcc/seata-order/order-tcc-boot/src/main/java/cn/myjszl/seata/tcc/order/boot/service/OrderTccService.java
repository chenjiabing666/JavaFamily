package cn.myjszl.seata.tcc.order.boot.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author 公众号：码猿技术专栏
 * TCC的事务模型
 */
@LocalTCC
public interface OrderTccService {

    /**
     * 一阶段的try方法，用于资源的预留
     * 该注解@BusinessActionContextParameter：用于设置参数到BusinessActionContext中，这样二阶段的confirm和cancel方法便能获取参数
     * 该注解@TwoPhaseBusinessAction：标记两阶段事务，commitMethod这个属性设置confirm方法名，rollbackMethod这个属性设置cancel方法名
     * @param businessActionContext 用于向第二阶段传递参数，比如全局事务ID，分支ID，入参....
     *
     */
    @TwoPhaseBusinessAction(name="orderTcc",commitMethod = "commit",rollbackMethod = "rollback")
    boolean tryCreate(BusinessActionContext businessActionContext,
                        @BusinessActionContextParameter(paramName = "userId") String userId,
                        @BusinessActionContextParameter(paramName = "orderId") String orderId,
                        @BusinessActionContextParameter(paramName = "productId") Long productId,
                        @BusinessActionContextParameter(paramName = "num") Long num);

    /**
     * 二阶段的confirm方法，用于事务的提交
     * @param businessActionContext 通过该参数能够获取一阶段的入参，全局事务ID.....
     * @return 返回true则表示成功执行，返回false则执行失败，Seata默认会不断的重试，直到成功
     */
    boolean commit(BusinessActionContext businessActionContext);

    /**
     * 二阶段的cancel方法，用于事务的回滚
     * @param businessActionContext 通过该参数能够获取一阶段的入参，全局事务ID.....
     * @return 返回true则表示成功执行，返回false则执行失败，Seata默认会不断的重试，直到成功
     */
    boolean rollback(BusinessActionContext businessActionContext);
}
