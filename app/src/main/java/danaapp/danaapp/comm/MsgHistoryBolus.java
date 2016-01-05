package danaapp.danaapp.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import danaapp.danaapp.SerialEngine;
import danaapp.danaapp.db.Bolus;

/**
 * Created by mike on 04.01.2016.
 *
 *  Receive 3 records starting at 2*page
 */
public class MsgHistoryBolus extends DanaRMessage {
    private static Logger log = LoggerFactory.getLogger(MsgHistoryBolus.class);
    List<Bolus> bolusList = new ArrayList<Bolus>();

     public MsgHistoryBolus(int page) {
        super("CMD_HISPAGE_MEAL_INS");
        SetCommand(SerialParam.CTRL_CMD_HISPAGE);
        SetSubCommand(SerialParam.DOWNLOAD_SUB_BOLUS);

        SetParamByte((byte) (page & 0xff));
        responses = 3;
    }

    public MsgHistoryBolus(String cmdName) {
        super(cmdName);
        responses = 3;
    }

    public MsgHistoryBolus() {
        super("CMD_HISPAGE_MEAL_INS");
        SetCommand(SerialParam.CTRL_CMD_HISPAGE);
        SetSubCommand(SerialParam.DOWNLOAD_SUB_BOLUS);
        responses = 3;
    }

    public void handleMessage(byte[] bytes) {
        //log.debug("MsgHistoryBolus response " + this.getMessageName() + " " + SerialEngine.toHexString(bytes));

        int page = DanaRMessages.byteArrayToInt(bytes, 0, 1);
        int result = DanaRMessages.byteArrayToInt(bytes, 1, 1); // 0x01...OK  0x63...previous record was last
        if (result == 0x01) {
            Date date = DanaRMessages.byteArrayToDate(bytes, 2);
            int something = DanaRMessages.byteArrayToInt(bytes, 7, 2); // == 0x0080
            double bolus = DanaRMessages.byteArrayToInt(bytes, 9, 2) * 0.01;
            log.debug("MsgHistoryBolus Command response: " + date.toString() + " " + bolus);
            Bolus newrec = new Bolus();
            newrec.timeStart = date;
            newrec.amount = bolus;
            bolusList.add(newrec);
        } else {
            // last record, do not read next
            responses = 1;
        }
    }

}
