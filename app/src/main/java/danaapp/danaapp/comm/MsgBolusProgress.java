package danaapp.danaapp.comm;

import danaapp.danaapp.MainApp;
import danaapp.danaapp.bolus.BolusUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;

public class MsgBolusProgress extends DanaRMessage {
    private static Logger log = LoggerFactory.getLogger(MsgBolusProgress.class);
    public static final DecimalFormat bolusNumberFormat = new DecimalFormat("0.0");

    private BolusUI bolusUI;
    private String _id;
    private int amount;

    public int progress = 0;

    public MsgBolusProgress() {
        super("CMD_PUMP_THIS_REMAINDER_MEAL_INS");
        SetCommand(SerialParam.CTRL_CMD_STATUS);
        SetSubCommand(SerialParam.CTRL_SUB_STATUS_BOLUS_PROGRESS);
    }

    public MsgBolusProgress(String cmdName) {
        super(cmdName);
    }

    public MsgBolusProgress(BolusUI bolusUI, int amount, String _id) {
        this();
        this.bolusUI = bolusUI;
        this.amount = amount;
        this. _id = _id;
    }

    public void handleMessage(byte[] bytes) {
        progress = DanaRMessages.byteArrayToInt(bytes, 0, 2);
        log.debug("remaining "+progress);
        bolusUI.bolusDeliveredAmountSoFar = progress/100d;
        bolusUI.bolusDelivering();
        MainApp.getNSClient().sendTreatmentStatusUpdate(this._id, "Delivering " + bolusNumberFormat.format((amount - progress) / 100d) + "U");
    }


}
