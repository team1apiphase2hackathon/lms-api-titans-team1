package requestPojo;

public class ProgramRequest {

    private String programDescription;
    private String programName;
    private String status;

    public void setProgramName(String programName) {
        this.programName = programName;
    }
    public String getProgramName() {
        return programName;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }




}
