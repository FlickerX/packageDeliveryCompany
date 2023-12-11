package delivery.kursinis.Enums;

public enum OrderStatus {
    PENDING, CANCELED, IN_PROGRESS, COMPLETED;


    public static OrderStatus[] getStatuses(){
        return values();
    }
}
