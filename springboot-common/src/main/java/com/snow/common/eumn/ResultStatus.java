package com.snow.common.eumn;

public enum ResultStatus {
    SUCCESS(100, "操作成功"),
    SUCCESS_ADD(101, "新增成功"),
    SUCCESS_DELETE(102, "删除成功"),
    SUCCESS_UPDATE(103, "修改成功"),
    SUCCESS_QUERY(104, "查询成功"),
    SUCCESS_VALIDATE(105, "验证成功"),
    SUCCESS_UPLOAD(106, "上传成功"),
    SUCCESS_CANCEL(107, "取消成功"),
    SUCCESS_INSTALL(108, "设置成功"),
    FAILED(200, "操作失败"),
    FAILED_ADD(201, "新增失败"),
    FAILED_DELETE(202, "删除失败"),
    FAILED_UPDATE(203, "修改失败"),
    FAILED_QUERY(204, "查询失败"),
    FAILED_VALIDATE(205, "验证失败"),
    FAILED_UPLOAD(206, "上传失败"),
    FAILED_CANCEL(207, "取消失败"),
    FAILED_DATA_VALIDATE(208, "数据校验失败"),
    EXCEPTION_SERVER(401, "服务器异常"),
    EXCEPTION_INFORMATION(402, "消息异常"),
    EXCEPTION_TOKEN(403, "Token处理异常");

    private int value;
    private String reasonPhrase;

    private ResultStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public static String getMessage(int value) {
        ResultStatus[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ResultStatus obj = var1[var3];
            if (obj.getValue() == value) {
                return obj.getReasonPhrase();
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "{value:" + this.value + ", reasonPhrase:'" + this.reasonPhrase + "'}";
    }
}
