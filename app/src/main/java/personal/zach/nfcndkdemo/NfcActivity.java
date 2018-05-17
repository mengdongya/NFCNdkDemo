package personal.zach.nfcndkdemo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ozner.nfc.BaseNFCActivity;
import com.ozner.nfc.CardBean.MonthCard;
import com.ozner.nfc.CardBean.OznerCard;
import com.ozner.nfc.CardBean.OznerCardType;
import com.ozner.nfc.OperationState;
import com.ozner.nfc.OznerNfcManager;

public class NfcActivity extends BaseNFCActivity {
    private static final String TAG = "NfcActivity";
    TextView tvResult;
    ProgressDialog dialog;
    Handler mHandler = new Handler();

    public static String ByteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvResult = (TextView) findViewById(R.id.tvResult);

        dialog = new  ProgressDialog(this);
        dialog.setMessage("正在读卡");
        if (!oznerNfcManager.hasNfc()) {
            Toast.makeText(this, "不支持NFC功能", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (!oznerNfcManager.isNfcEnable()) {
            new AlertDialog.Builder(this, android.app.AlertDialog.THEME_HOLO_LIGHT)
                    .setMessage("打开NFC")
                    .setPositiveButton("好", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            oznerNfcManager.openNfc();
                        }
                    })
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            NfcActivity.this.finish();
                        }
                    }).show();
        }
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_nfc;
    }

    @Override
    protected OznerNfcManager.IOnzerNfcOpera getNfcOpera() {
        return new OznerNfcManager.IOnzerNfcOpera() {
            @Override
            public void dealWork() {
//                oznerNfcManager.readMonthCard(new OznerNfcManager.IMonthCardListener() {
//                    @Override
//                    public void onResult(int i, String s, final MonthCard monthCard) {
//                        if (i == OperationState.Result_Ok) {
//                            Log.e(TAG, "onResult: " + monthCard.toString());
//                            try {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        tvResult.setText(monthCard.toString());
//
//                                    }
//                                });
//                            } catch (Exception ex) {
//                                Log.e(TAG, "onResult_Ex: " + ex.getMessage());
//                            }
//                        } else {
//                            onErrorMessage("state:" + i + " , Msg:" + s);
//                        }
//                    }
//                });

//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialog.show();
//                    }
//                });
//
//                oznerNfcManager.readAllBlocksData(new OznerNfcManager.INfcReaderListener() {
//                    @Override
//                    public void onResult(int i, String s, final OznerCard oznerCard) {
//                        if (i == OperationState.Result_Ok) {
//                            final String cardtype = new String((oznerCard.blockDatas.get(Integer.valueOf(1))).data);
//                            try {
//                                if ("00000000000000-1".equals(cardtype)) {
//                                    mHandler.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            if (dialog.isShowing()){
//                                                dialog.cancel();
//                                            }
//                                            tvResult.setText("白卡，无卡面值等信息");
//                                        }
//                                    });
////                                Message message = new Message();
////                                message.obj = "白卡，无卡面值等信息";
////                                message.what = ReadCardInfo;
////                                handler.sendMessage(message);
//                                } else {
//
//                                    mHandler.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            if (dialog.isShowing()){
//                                                dialog.cancel();
//                                            }
//                                            String cardinfoText = "卡类型:" + getCardType(Integer.valueOf(cardtype)) +
//                                                    "; 卡号:" + oznerCard.cardId +
//                                                    "; 卡面值:" + String.valueOf(Integer.valueOf(new String((oznerCard.blockDatas.get(Integer.valueOf(4))).data))) +
//                                                    "; 包月值:" + String.valueOf(Integer.valueOf(new String((oznerCard.blockDatas.get(Integer.valueOf(5))).data))) +
//                                                    "; 区域代码:" + String.valueOf(Integer.valueOf(new String((oznerCard.blockDatas.get(Integer.valueOf(6))).data))) +
//                                                    "; 二级区域代码" + String.valueOf(Integer.valueOf(new String((oznerCard.blockDatas.get(Integer.valueOf(9))).data))) +
//                                                    "; 机型:" + String.valueOf("A" + (Integer.valueOf(new String((oznerCard.blockDatas.get(Integer.valueOf(8))).data)) - 10));
//
//                                            tvResult.setText(cardinfoText);
//                                        }
//                                    });
//
////                                Message message = new Message();
////                                message.obj = cardinfoText;
////                                message.what = ReadCardInfo;
////                                handler.sendMessage(message);
//                                }
//
//                            } catch (Exception ex) {
//                                Log.e(TAG, "onResult_Ex: " + ex.getMessage());
//                            }
//
//                        }else {
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (dialog.isShowing()){
//                                        dialog.dismiss();
//                                    }
//                                    tvResult.setText("读取数据异常");
//                                }
//                            });
//                        }
//                    }
//                });


                oznerNfcManager.readAllBlocksData(new OznerNfcManager.INfcReaderListener() {
                    @Override
                    public void onResult(int i, String s, final OznerCard oznerCard) {
                        Log.e(TAG, "onResult: 错误信息：" + i + "->" + s);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvResult.setText("");
                            }
                        });
                        Log.e(TAG, "onResult: 卡号->"+oznerCard.cardId );
//                        oznerCard.cardId
                        for (int j = 0; j < oznerCard.blockDatas.size(); j++) {

                            final String res = j + "->" + new String(oznerCard.blockDatas.get(j).data) + "\n";
                            if (j == 0) {
                                Log.e(TAG, "onResult: 0块数据->" + ByteArrayToHexString(oznerCard.blockDatas.get(0).data));
                            }


                            Log.e(TAG, "onResult: " + res);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvResult.append(res);

                                }
                            });
                        }
                    }
                });
            }
        };
    }

    private String getCardType(int cardType) {
        String tempType;
        switch (cardType) {
            case OznerCardType.Type_Clear:
                tempType = "清除卡";
                break;
            case OznerCardType.Type_AreaCode:
                tempType = "区域代码卡";
                break;
            case OznerCardType.Type_ChangeAreaCode:
                tempType = "区域代码修改卡";
                break;
            case OznerCardType.Type_Cipher:
                tempType = "密码卡";
                break;
            case OznerCardType.Type_DeviceNum:
                tempType = "机号卡";
                break;
            case OznerCardType.Type_DeviceType:
                tempType = "专用机型卡";
                break;
            case OznerCardType.Type_DeviceTypeNormal:
                tempType = "通用机型卡";
                break;
            case OznerCardType.Type_MonthCard:
                tempType = "包月卡";
                break;
            case OznerCardType.Type_Test:
                tempType = "测试卡";
                break;
            case OznerCardType.Type_MonthDeviceType:
                tempType = "包月机型卡";
                break;
            case OznerCardType.Type_Personal:
                tempType = "个人卡";
                break;
            default:
                tempType = "未知";
        }
        return tempType;
    }

    @Override
    protected void onErrorMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onErrorMessage: " + s);
    }
}
