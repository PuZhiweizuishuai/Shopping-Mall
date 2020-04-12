package com.buguagaoshu.common.constant;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-12 18:18
 */
public class WareConstant {
    public enum PurchaseStatusEnum {
        /**
         * 采购单状态
         */
        CREATED(0, "新建"),
        ASSIGNED(1, "已分配"),
        RECEIVE(2, "已领取"),
        FINISH(3, "已完成"),
        HAS_ERROR(4, "有异常");
        private int code;
        private String msg;

        PurchaseStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static String getMsg(int code) {
            for (PurchaseStatusEnum purchaseStatusEnum : PurchaseStatusEnum.values()) {
                if (purchaseStatusEnum.getCode() == code) {
                    return purchaseStatusEnum.msg;
                }
            }
            return "碰到未知异常!";
        }
    }


    public enum PurchaseDetailStatusEnum {
        /**
         * 采购订单分配状态
         */
        CREATED(0, "新建"),
        ASSIGNED(1, "已分配"),
        BUYING(2, "正在采购"),
        FINISH(3, "已完成"),
        HAS_ERROR(4, "采购失败"),
        SOME_ERROR(5, "部分成功。部分失败");

        private int code;
        private String msg;

        PurchaseDetailStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
