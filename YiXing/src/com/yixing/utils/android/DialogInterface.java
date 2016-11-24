package com.yixing.utils.android;


import com.yixing.model.RedPacketModel;
import com.yixing.model.TicketModel;

public interface DialogInterface {
	public void selectRedPacket(boolean select ,RedPacketModel models);
	public void selectTicket(boolean select ,TicketModel models);
}
