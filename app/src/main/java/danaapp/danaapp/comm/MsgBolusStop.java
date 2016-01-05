package danaapp.danaapp.comm;

import danaapp.danaapp.MainApp;
import danaapp.danaapp.bolus.BolusUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgBolusStop extends DanaRMessage {
    private static Logger log = LoggerFactory.getLogger(MsgBolusStop.class);
    private BolusUI bolusUI;
    private String _id;

    public boolean stopped = false;

    public MsgBolusStop() {
        super("CMD_MEALINS_STOP");
        SetCommand(SerialParam.CTRL_CMD_BOLUS);
        SetSubCommand(SerialParam.CTRL_SUB_BOLUS_STOP);
    }

    public MsgBolusStop(String cmdName) {
        super(cmdName);
    }

    public MsgBolusStop(BolusUI bolusUI, String _id) {
        this();
        this.bolusUI = bolusUI;
        this._id = _id;
    }


    public void handleMessage(byte[] bytes) {

        stopped = true;
        bolusUI.bolusFinished();
        MainApp.getNSClient().sendTreatmentStatusUpdate(this._id, "Delivered");
    }


}
