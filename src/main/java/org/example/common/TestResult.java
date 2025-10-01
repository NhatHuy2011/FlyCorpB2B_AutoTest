package org.example.common;

public class TestResult {
    public String actualMessage;
    public boolean isPassed;

    public TestResult(String actualMessage, boolean isPassed) {
        this.actualMessage = actualMessage;
        this.isPassed = isPassed;
    }

    public String getActualMessage() {
        return actualMessage;
    }

    public void setActualMessage(String actualMessage) {
        this.actualMessage = actualMessage;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }
}
