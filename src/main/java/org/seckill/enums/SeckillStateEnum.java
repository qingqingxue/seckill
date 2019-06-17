package org.seckill.enums;

/**
 * 枚举秒杀操作中的常量数据
 */
public enum SeckillStateEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀失败"),
    REPEATE_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改");
    //秒杀结果执行状态
    private int state;
    //状态表示
    private String stateInfo;

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    /**
     * 枚举所有状态
     * @param index
     * @return
     */
    public static SeckillStateEnum stateOf(int index){
        for(SeckillStateEnum state : values()){
         if(state.getState() == index){
             return state;
         }
        }
        return null;
    }
}
