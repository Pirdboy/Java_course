package cqu.shy.data;

import java.io.Serializable;

public class DataPacket implements Serializable{
	private String packetType;
    private Object data=null;
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
    	if(packetType.equals("Text"))  //如果该包是传输文字
    	{
    		this.data = (String)data;
    	}
    	else if(packetType.equals("Image")) //如果该包是传输图像
    	{
    		this.data = data;
    	}
    	else if(packetType.equals("Graph"))
    	{
    		this.data = (Graph)data;
    	}
    }
}
