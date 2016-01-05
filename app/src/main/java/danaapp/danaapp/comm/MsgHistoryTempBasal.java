package danaapp.danaapp.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import danaapp.danaapp.SerialEngine;
import danaapp.danaapp.db.Bolus;

/**
 * Created by mike on 05.01.2016.
 */
public class MsgHistoryTempBasal extends DanaRMessage {
    private static Logger log = LoggerFactory.getLogger(MsgHistoryBolus.class);
    List<Bolus> bolusList = new ArrayList<Bolus>();

    public MsgHistoryTempBasal(int page) {
        super("CMD_HISPAGE_BASAL_RECORD");
        SetCommand(SerialParam.CTRL_CMD_HISPAGE);
        SetSubCommand(SerialParam.RECORD_TYPE_BASALHOUR);

        //SetParamInt(page);
        SetParamByte((byte) (116));
        SetParamByte((byte) (0));
        SetParamByte((byte) (4));
        SetParamByte((byte) (0));
        SetParamByte((byte) (0));
        SetParamByte((byte) (0));
                //responses = 3;
    }

    public MsgHistoryTempBasal(String cmdName) {
        super(cmdName);
        //responses = 3;
    }

    public MsgHistoryTempBasal() {
        super("CMD_HISPAGE_BASAL_RECORD");
        SetCommand(SerialParam.CTRL_CMD_HISPAGE);
        SetSubCommand(SerialParam.RECORD_TYPE_BASALHOUR);
        //responses = 3;
    }

    public void handleMessage(byte[] bytes) {
        log.debug("MsgHistoryTempBasal response " + this.getMessageName() + " " + SerialEngine.toHexString(bytes));
return;
        /*
        int page = DanaRMessages.byteArrayToInt(bytes, 0, 1);
        int result = DanaRMessages.byteArrayToInt(bytes, 1, 1); // 0x01...OK  0x63...previous record was last
        if (result == 0x01) {
            Date date = DanaRMessages.byteArrayToDate(bytes, 2);
            int something = DanaRMessages.byteArrayToInt(bytes, 7, 2); // == 0x0080
            double bolus = DanaRMessages.byteArrayToInt(bytes, 9, 2) * 0.01;
            log.debug("MsgHistoryTempBasal Command response: " + date.toString() + " " + bolus);
            Bolus newrec = new Bolus();
            newrec.timeStart = date;
            newrec.amount = bolus;
            bolusList.add(newrec);
        } else {
            // last record, do not read next
            responses = 1;
        }
        */
    }


}
