package Model;

public class Room
{
    private int roomID;
    private String roomType;

    public Room(int roomID, String roomType)
    {
        this.roomID = roomID;
        this.roomType = roomType;
    }

    public int getRoomID()
    {
        return roomID;
    }

    public void setRoomID(int roomID)
    {
        this.roomID = roomID;
    }

    public String getRoomType()
    {
        return roomType;
    }

    public void setRoomType(String roomType)
    {
        this.roomType = roomType;
    }

    @Override
    public String toString()
    {
        return getRoomType();
    }
}
