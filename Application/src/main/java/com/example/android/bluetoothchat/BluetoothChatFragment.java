/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.bluetoothchat;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.util.Calendar;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.ToggleButton;
import android.widget.CheckBox;
import android.content.Context;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.CompoundButton;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.TimePicker;

import android.widget.SeekBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.app.ActionBar;
import android.widget.Button;
import android.widget.EditText;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Spinner;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.common.logger.Log;

/**
 * This fragment controls Bluetooth to communicate with other devices.
 */
public class BluetoothChatFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "BluetoothChatFragment";
    private Spinner spinner1, schedspin;
    private Button btnSubmit;
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
    private Switch switch1, switch2, switch3, switch4;
    private boolean issw1checked;
    int set =40;
    SeekBar sb1, sb2, sb3, sb4;
    private int bs1, bs2, bs3, bs4;
    private int sw1, sw2, sw3, sw4;
    private TextView textView, textView2,textView3,textView4;
    private String message, message1, message2, message3;
    private TimePicker tpicker;
    private ListView mConversationView;
    private EditText mOutEditText;
    private int hour, minutes;
   // private Button mSendButton;
    private Button sync;
    private EditText r1, r2, r3, r4;
    private String newVar;
    private String newVar1;
    private String newVar2;
    private String newVar3;
    private String mConnectedDeviceName = null;
    private String Days, Sched, day, sche;
    private ArrayAdapter<String> mConversationArrayAdapter;
    private String hourz, minutez;
    private StringBuffer mOutStringBuffer;

    private BluetoothAdapter mBluetoothAdapter = null;

    private BluetoothChatService mChatService = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            FragmentActivity activity = getActivity();
            Toast.makeText(activity, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            activity.finish();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mChatService == null) {
            setupChat();
        }
        sb1 = (SeekBar) getView().findViewById(R.id.seekBar2);
        sb2 = (SeekBar) getView().findViewById(R.id.seekBar3);
        sb3 = (SeekBar) getView().findViewById(R.id.seekBar4);
        sb4 = (SeekBar) getView().findViewById(R.id.seekBar5);

        sb1.setOnSeekBarChangeListener(this);
        sb2.setOnSeekBarChangeListener(this);
        sb3.setOnSeekBarChangeListener(this);
        sb4.setOnSeekBarChangeListener(this);
        ((TextView) getView().findViewById(R.id.editText)).setText("nl");
        ((TextView) getView().findViewById(R.id.editText2)).setText("nl");
        ((TextView) getView().findViewById(R.id.editText3)).setText("nl");
        ((TextView) getView().findViewById(R.id.editText4)).setText("nl");
        tpicker =(TimePicker) getView().findViewById(R.id.timePicker);
        hour = tpicker.getCurrentHour();
        minutes = tpicker.getCurrentMinute();
        hourz =Integer.toString(hour);
        minutez =Integer.toString(minutes);
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
        updatetime();
        bs1 = sb1.getProgress();
        bs2 = sb2.getProgress();
        bs3 = sb3.getProgress();
        bs4 = sb4.getProgress();
        switch1 = (Switch) getView().findViewById(R.id.switch1);
        switch2 = (Switch) getView().findViewById(R.id.switch2);
        switch3 = (Switch) getView().findViewById(R.id.switch3);
        switch4 = (Switch) getView().findViewById(R.id.switch4);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sw1 =1;
                    ((TextView) getView().findViewById(R.id.editText)).setText(bs1 + 40 + "");
                }
                else  {
                    sw1=0;
                    ((TextView) getView().findViewById(R.id.editText)).setText("nl");
                }
            }
        });

        if (sw1==1){
            ((TextView) getView().findViewById(R.id.editText)).setText(bs1 + 40 + "");
        }
        else {
            ((TextView) getView().findViewById(R.id.editText)).setText("nl");
        }
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sw2 =1;
                    ((TextView) getView().findViewById(R.id.editText2)).setText(bs2 + 40 + "");
                }
                else  {
                    sw2=0;
                    ((TextView) getView().findViewById(R.id.editText2)).setText("nl");
                }
            }
        });

        if (sw2==1){
            ((TextView) getView().findViewById(R.id.editText2)).setText(bs2 + 40 + "");
        }
        else if (sw2==0) {
            ((TextView) getView().findViewById(R.id.editText2)).setText("nl");
        }
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sw3 =1;
                    ((TextView) getView().findViewById(R.id.editText3)).setText(bs3 + 40 + "");
                }
                else  {
                    sw3=0;
                    ((TextView) getView().findViewById(R.id.editText3)).setText("nl");
                }
            }
        });

        if (sw3==1){
            ((TextView) getView().findViewById(R.id.editText3)).setText(bs3 + 40 + "");
        }
        else if (sw3==0) {
            ((TextView) getView().findViewById(R.id.editText3)).setText("nl");
        }
        switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sw4 =1;
                    ((TextView) getView().findViewById(R.id.editText4)).setText(bs4 + 40 + "");
                }
                else  {
                    sw4=0;
                    ((TextView) getView().findViewById(R.id.editText4)).setText("nl");
                }
            }
        });

        if (sw4==1){
            ((TextView) getView().findViewById(R.id.editText4)).setText(bs4 + 40 + "");
        }
        else if (sw4==0) {
            ((TextView) getView().findViewById(R.id.editText4)).setText("nl");
        }

    }
    public void addListenerOnSpinnerItemSelection() {

        schedspin = (Spinner) getView().findViewById(R.id.schedspin);

        schedspin.setOnItemSelectedListener(new CustomonItemSelectedListener());
    }
    // get the selected dropdown list value

    private void updatevalues() {

        bs1 = sb1.getProgress();
        bs2 = sb2.getProgress();
        bs3 = sb3.getProgress();
        bs4 = sb4.getProgress();
        switch1 = (Switch) getView().findViewById(R.id.switch1);
        switch2 = (Switch) getView().findViewById(R.id.switch2);
        switch3 = (Switch) getView().findViewById(R.id.switch3);
        switch4 = (Switch) getView().findViewById(R.id.switch4);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                     sw1 =1;
                    ((TextView) getView().findViewById(R.id.editText)).setText(bs1 + 40 + "");
                }
                else  {
                    sw1=0;
                    ((TextView) getView().findViewById(R.id.editText)).setText("nl");
                }
            }
        });

        if (sw1==1){
            ((TextView) getView().findViewById(R.id.editText)).setText(bs1 + 40 + "");
        }
        else {
            ((TextView) getView().findViewById(R.id.editText)).setText("nl");
        }
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sw2 =1;
                    ((TextView) getView().findViewById(R.id.editText2)).setText(bs2 + 40 + "");
                }
                else  {
                    sw2=0;
                    ((TextView) getView().findViewById(R.id.editText2)).setText("nl");
                }
            }
        });

        if (sw2==1){
            ((TextView) getView().findViewById(R.id.editText2)).setText(bs2 + 40 + "");
        }
        else if (sw2==0) {
            ((TextView) getView().findViewById(R.id.editText2)).setText("nl");
        }
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sw3 =1;
                    ((TextView) getView().findViewById(R.id.editText3)).setText(bs3 + 40 + "");
                }
                else  {
                    sw3=0;
                    ((TextView) getView().findViewById(R.id.editText3)).setText("nl");
                }
            }
        });

        if (sw3==1){
            ((TextView) getView().findViewById(R.id.editText3)).setText(bs3 + 40 + "");
        }
        else if (sw3==0) {
            ((TextView) getView().findViewById(R.id.editText3)).setText("nl");
        }
        switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sw4 =1;
                    ((TextView) getView().findViewById(R.id.editText4)).setText(bs4 + 40 + "");
                }
                else  {
                    sw4=0;
                    ((TextView) getView().findViewById(R.id.editText4)).setText("nl");
                }
            }
        });

        if (sw4==1){
            ((TextView) getView().findViewById(R.id.editText4)).setText(bs4 + 40 + "");
        }
        else if (sw4==0) {
            ((TextView) getView().findViewById(R.id.editText4)).setText("nl");
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        updatevalues();

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatService != null) {
            mChatService.stop();
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bluetooth_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mConversationView = (ListView) view.findViewById(R.id.in);
       // mOutEditText = (EditText) view.findViewById(R.id.edit_text_out);
        r1 = (EditText) view.findViewById(R.id.editText);
        r2 = (EditText) view.findViewById(R.id.editText2);
        r3 = (EditText) view.findViewById(R.id.editText3);
        r4 = (EditText) view.findViewById(R.id.editText4);

     //   mSendButton = (Button) view.findViewById(R.id.button_send);
      //  save = (Button) view.findViewById(R.id.save);

    }

    /**
     * Set up the UI and background operations for chat.
     */
    private void updatetime(){
       //method that converts time
        if (hourz.equals("1")) {
            hourz = ("01");
        }
        if (hourz.equals("2")) {
            hourz = ("02");
        }
        if (hourz.equals("3")) {
            hourz = ("03");
        }
        if (hourz.equals("4")) {
            hourz = ("04");
        }
        if (hourz.equals("5")) {
            hourz = ("05");
        }
        if (hourz.equals("6")) {
            hourz = ("06");
        }
        if (hourz.equals("7")) {
            hourz = ("07");
        }
        if (hourz.equals("8")) {
            hourz = ("08");
        }
        if (hourz.equals("9")) {
            hourz = ("09");
        }
        if (minutez.equals("1")) {
            minutez = ("01");
        }
        if (minutez.equals("2")) {
            minutez = ("02");
        }
        if (minutez.equals("3")) {
            minutez = ("03");
        }
        if (minutez.equals("4")) {
            minutez = ("04");
        }
        if (minutez.equals("5")) {
            minutez = ("05");
        }
        if (minutez.equals("6")) {
            minutez = ("06");
        }
        if (minutez.equals("7")) {
            minutez = ("07");
        }
        if (minutez.equals("8")) {
            minutez = ("08");
        }
        if (minutez.equals("9")) {
            minutez = ("09");
        }
    }
    private void setupChat() {
        Log.d(TAG, "setupChat()");
        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.message);
        mConversationView.setAdapter(mConversationArrayAdapter);
        mChatService = new BluetoothChatService(getActivity(), mHandler);
        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    public void addListenerOnButton() {
        schedspin = (Spinner) getView().findViewById(R.id.schedspin);
        btnSubmit = (Button) getView().findViewById(R.id.savespin);
        sync = (Button) getView().findViewById(R.id.sync);
        sync.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                updatetime();
                sendMessage("U"+hourz+minutez);
               // try { Thread.sleep(4000); } catch (Exception e) { return ;}
            }
        });
        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                textView = (TextView) getView().findViewById(R.id.editText);
                message = textView.getText().toString();
                if(message.equals("nl")){
                    message="xx";
                }
                textView2 = (TextView) getView().findViewById(R.id.editText2);
                message1 = textView2.getText().toString();
                if(message1.equals("nl")){
                    message1="xx";
                }
                textView3 = (TextView) getView().findViewById(R.id.editText3);
                message2 = textView3.getText().toString();
                if(message2.equals("nl")){
                    message2="xx";
                }
                textView4 = (TextView) getView().findViewById(R.id.editText4);
                message3 = textView4.getText().toString();
                if(message3.equals("nl")){
                    message3="xx";
                }
                hour = tpicker.getCurrentHour();
                minutes = tpicker.getCurrentMinute();
                Sched = String.valueOf(schedspin.getSelectedItem());
                tpicker =(TimePicker) getView().findViewById(R.id.timePicker);
                hour = tpicker.getCurrentHour();
                minutes = tpicker.getCurrentMinute();
                hourz =Integer.toString(hour);
                minutez =Integer.toString(minutes);
                addListenerOnButton();
                addListenerOnSpinnerItemSelection();
                updatetime();
                char c= Sched.charAt(15);
                if(c=='y'){
                    hourz="xx";
                    minutez="xx";
                }
                ((TextView) getView().findViewById(R.id.spintext)).setText("z"+message+message1+message2+message3+c+hourz+minutez);
                sendMessage("z"+message+message1+message2+message3+c+hourz+minutez);
               // sendMessage("c74727274n404040400600w404040402200");
                try { Thread.sleep(4000); } catch (Exception e) { return ;}
            }
        });
    }

    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(getActivity(), R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
           // mOutEditText.setText(mOutStringBuffer);
        }
    }

    /**
     * The action listener for the EditText widget, to listen for the return key
     */
    private TextView.OnEditorActionListener mWriteListener
            = new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            // If the action is a key-up event on the return key, send the message
            if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                String message = view.getText().toString();
                sendMessage(message);
            }
            return true;
        }
    };

    /**
     * Updates the status on the action bar.
     *
     * @param resId a string resource ID
     */
    private void setStatus(int resId) {
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(resId);
    }

    /**
     * Updates the status on the action bar.
     *
     * @param subTitle status
     */
    private void setStatus(CharSequence subTitle) {
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            FragmentActivity activity = getActivity();
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            mConversationArrayAdapter.clear();

                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);

                    // USE FOR DETERMINING ROOM TEMPERATURES SENT FROM BLUETOOTH TO PHONE

                       ((TextView) getView().findViewById(R.id.r1temp)).setText(" "+readMessage.charAt(1)+readMessage.charAt(2)+" ");
                       ((TextView) getView().findViewById(R.id.r2temp)).setText(" "+readMessage.charAt(3)+readMessage.charAt(4)+" ");
                       ((TextView) getView().findViewById(R.id.r3temp)).setText(" "+readMessage.charAt(5)+readMessage.charAt(6)+" ");
                       ((TextView) getView().findViewById(R.id.r4temp)).setText(" "+readMessage.charAt(7)+readMessage.charAt(8)+" ");
                    ((TextView) getView().findViewById(R.id.textView16)).setText("Room 1: "+readMessage.charAt(10)+readMessage.charAt(11)+" and "+readMessage.charAt(23)+readMessage.charAt(24)+" degrees");
                    ((TextView) getView().findViewById(R.id.textView2)).setText("Room 2: "+readMessage.charAt(12)+readMessage.charAt(13)+" and "+readMessage.charAt(25)+readMessage.charAt(26)+" degrees");
                    ((TextView) getView().findViewById(R.id.textView18)).setText("Room 3: "+readMessage.charAt(14)+readMessage.charAt(15)+" and "+readMessage.charAt(27)+readMessage.charAt(28)+" degrees");
                    ((TextView) getView().findViewById(R.id.textView20)).setText("Room 4: "+readMessage.charAt(16)+readMessage.charAt(17)+" and "+readMessage.charAt(29)+readMessage.charAt(30)+" degrees");
                    ((TextView) getView().findViewById(R.id.textView7)).setText("Scheduled Event Times: "+readMessage.charAt(18)+readMessage.charAt(19)+":"+readMessage.charAt(20)+readMessage.charAt(21)+" and "+readMessage.charAt(31)+readMessage.charAt(32)+":"+readMessage.charAt(33)+readMessage.charAt(34));
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != activity) {
                        Toast.makeText(activity, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(getActivity(), R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
        }
    }

    /**
     * Establish connection with other divice
     *
     * @param data   An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bluetooth_chat, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.secure_connect_scan: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                return true;
            }
            case R.id.insecure_connect_scan: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(getActivity(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
                return true;
            }
            case R.id.discoverable: {
                // Ensure this device is discoverable by others
                ensureDiscoverable();
                return true;
            }
        }
        return false;
    }

}