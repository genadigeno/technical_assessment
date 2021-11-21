package com.task.core.messaging;

public enum TaskStatus {
    RECEIVED("\u10DB\u10D8\u10E6\u10D4\u10D1\u10E3\u10DA\u10D8\u10D0 \u10D3\u10D0\u10E1\u10D0\u10DB\u10E3\u10E8\u10D0\u10D5\u10D4\u10D1\u10DA\u10D0\u10D3"),
    IN_PROCESS("\u10E8\u10D4\u10E1\u10E0\u10E3\u10DA\u10D4\u10D1\u10D8\u10E1 \u10DE\u10E0\u10DD\u10EA\u10D4\u10E1\u10E8\u10D8\u10D0"),
    SUCCESSFUL("\u10EC\u10D0\u10E0\u10DB\u10D0\u10E2\u10D4\u10D1\u10E3\u10DA\u10D8\u10D0"),
    FAILED("\u10EC\u10D0\u10E0\u10E3\u10DB\u10D0\u10E2\u10D4\u10D1\u10D4\u10DA\u10D8\u10D0");

    private String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
