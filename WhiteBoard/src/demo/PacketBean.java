package demo;

import java.io.Serializable;

//数据包类
public class PacketBean implements Serializable {

    private String packetType;
    private Object data;
    
    public String getPacketType() {
        return packetType;
    }
    public void setPacketType(String packetType) {
        this.packetType = packetType;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    
}