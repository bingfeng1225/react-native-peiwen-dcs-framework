package com.qd.peiwen.dcsframework.devices;

import android.content.Context;

import com.qd.peiwen.dcsframework.devices.audioplayer.AudioPlayerModule;
import com.qd.peiwen.dcsframework.devices.navigation.NavigationModule;
import com.qd.peiwen.dcsframework.devices.phone.PhoneModule;
import com.qd.peiwen.dcsframework.devices.phonebill.PhoneBillModule;
import com.qd.peiwen.dcsframework.devices.screen.ScreenModule;
import com.qd.peiwen.dcsframework.devices.sms.SMSModule;
import com.qd.peiwen.dcsframework.devices.speakercontroller.SpeakerControllerModule;
import com.qd.peiwen.dcsframework.devices.system.SystemModule;
import com.qd.peiwen.dcsframework.devices.textinput.TextInputModule;
import com.qd.peiwen.dcsframework.devices.ticket.TicketModule;
import com.qd.peiwen.dcsframework.devices.voiceoutput.VoiceOutputModule;
import com.qd.peiwen.dcsframework.devices.voicerecognize.VoiceRecognizeModule;


/**
 * Created by nick on 2017/11/29.
 */

public class ModuleFactory {

    private SMSModule smsModule;
    private PhoneModule phoneModule;
    private SystemModule systemModule;
    private ScreenModule screenModule;
    private TextInputModule textInputModule;
    private VoiceOutputModule voiceOutputModule;
    private AudioPlayerModule audioPlayerModule;
    private VoiceRecognizeModule voiceRecognizeModule;
    private SpeakerControllerModule speakerControllerModule;
    private NavigationModule navigationModule;
    private PhoneBillModule phoneBillModule;
    private TicketModule ticketModule;

    public ModuleFactory() {

    }

    public SMSModule smsModule(Context context) {
        if (null == smsModule) {
            this.smsModule = new SMSModule(context);
        }
        return this.smsModule;
    }

    public PhoneModule phoneModule(Context context) {
        if (null == phoneModule) {
            this.phoneModule = new PhoneModule(context);
        }
        return this.phoneModule;
    }

    public SystemModule systemModule(Context context) {
        if (null == systemModule) {
            this.systemModule = new SystemModule(context);
        }
        return this.systemModule;
    }

    public ScreenModule screenModule(Context context) {
        if (null == screenModule) {
            this.screenModule = new ScreenModule(context);
        }
        return this.screenModule;
    }

    public TextInputModule textInputModule(Context context) {
        if (null == textInputModule) {
            this.textInputModule = new TextInputModule(context);
        }
        return this.textInputModule;
    }

    public VoiceOutputModule voiceOutputModule(Context context) {
        if (null == voiceOutputModule) {
            this.voiceOutputModule = new VoiceOutputModule(context);
        }
        return this.voiceOutputModule;
    }

    public AudioPlayerModule audioPlayerModule(Context context) {
        if (null == audioPlayerModule) {
            this.audioPlayerModule = new AudioPlayerModule(context);
        }
        return this.audioPlayerModule;
    }


    public VoiceRecognizeModule voiceRecognizeModule(Context context) {
        if (null == voiceRecognizeModule) {
            this.voiceRecognizeModule = new VoiceRecognizeModule(context);
        }
        return this.voiceRecognizeModule;
    }

    public NavigationModule navigationModule(Context context) {
        if (null == navigationModule) {
            this.navigationModule = new NavigationModule(context);
        }
        return this.navigationModule;
    }

    public PhoneBillModule phoneBillModule(Context context) {
        if (null == phoneBillModule) {
            this.phoneBillModule = new PhoneBillModule(context);
        }

        return this.phoneBillModule;
    }

    public TicketModule ticketModule(Context context) {
        if (null == ticketModule) {
            this.ticketModule = new TicketModule(context);
        }
        return this.ticketModule;
    }

    public SpeakerControllerModule speakerControllerModule(Context context) {
        if (null == speakerControllerModule) {
            this.speakerControllerModule = new SpeakerControllerModule(context);
        }
        return this.speakerControllerModule;
    }

}
