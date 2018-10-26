package openstack.contributhon.com.openstackcontroller;

public class ErrorMessage {

    BadRequest badRequest;
    ConflictingRequest conflictingRequest;

    public String getMesage(int id) {
        switch(id) {
            case 0:
                return badRequest.message;
            case 1:
                return conflictingRequest.message;
        }
        return "";
    }

    public static class BadRequest {
        String message;
    }
    public static class ConflictingRequest {
        String message;
    }
}