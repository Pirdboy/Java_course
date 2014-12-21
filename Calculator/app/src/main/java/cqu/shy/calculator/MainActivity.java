package cqu.shy.calculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cqu.shy.arithmetic.Arithmetic;
import cqu.shy.arithmetic.Element;
import cqu.shy.arithmetic.ElementType;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private TextView resultText;
    private EditText expText;private RadioButton radianBtn;private RadioButton degreeBtn;
    private Button historyBtn;private Button saveBtn;private Button acBtn;
    private Button sinBtn;private Button cosBtn;private Button tanBtn;
    private Button lnBtn;private Button backspaceBtn;private Button leftPaBtn;
    private Button sevenBtn;private Button eightBtn;private Button nineBtn;private Button addBtn;
    private Button rightPaBtn;private Button fourBtn;private Button fiveBtn;
    private Button sixBtn;private Button subBtn;private Button powBtn;
    private Button oneBtn;private Button twoBtn;private Button threeBtn;
    private Button multiBtn;private Button sqrtBtn;private Button zeroBtn;
    private Button dotBtn;private Button equalBtn;private Button divisionBtn;

    private int startIndex=-1,endIndex=-1;
    private boolean startInputNum=false;
    private boolean equalfinished=false;
    private StringBuffer numBuffer = new StringBuffer();
    private ArrayList<Element> infix = new ArrayList<Element>(30);
    private List<Double> historyDatas = new ArrayList<Double>(10);

    private void initVariable(){
        startIndex=-1;endIndex=-1;
        startInputNum=false;
        equalfinished=false;
    }
    private void initViews(){
        expText = (EditText)findViewById(R.id.ExpText);
        expText.setOnTouchListener(null);
        //expText.setInputType(InputType.TYPE_NULL);
        expText.setFocusable(false);

        resultText = (TextView)findViewById(R.id.ResultText);
        radianBtn = (RadioButton)findViewById(R.id.radianBtn);radianBtn.setOnClickListener(this);
        degreeBtn = (RadioButton)findViewById(R.id.degreeBtn);degreeBtn.setOnClickListener(this);
        historyBtn = (Button)findViewById(R.id.historyBtn);historyBtn.setOnClickListener(this);
        saveBtn = (Button)findViewById(R.id.saveBtn);saveBtn.setOnClickListener(this);
        acBtn = (Button)findViewById(R.id.acBtn);acBtn.setOnClickListener(this);
        sinBtn = (Button)findViewById(R.id.sinBtn);sinBtn.setOnClickListener(this);
        cosBtn = (Button)findViewById(R.id.cosBtn);cosBtn.setOnClickListener(this);
        tanBtn = (Button)findViewById(R.id.tanBtn);tanBtn.setOnClickListener(this);
        lnBtn = (Button)findViewById(R.id.lnBtn);lnBtn.setOnClickListener(this);
        backspaceBtn = (Button)findViewById(R.id.backBtn);backspaceBtn.setOnClickListener(this);
        leftPaBtn = (Button)findViewById(R.id.leftPaBtn);leftPaBtn.setOnClickListener(this);
        sevenBtn = (Button)findViewById(R.id.sevenBtn);sevenBtn.setOnClickListener(this);
        eightBtn = (Button)findViewById(R.id.eightBtn);eightBtn.setOnClickListener(this);
        nineBtn = (Button)findViewById(R.id.nineBtn);nineBtn.setOnClickListener(this);
        addBtn = (Button)findViewById(R.id.addBtn);addBtn.setOnClickListener(this);
        rightPaBtn = (Button)findViewById(R.id.rightPaBtn);rightPaBtn.setOnClickListener(this);
        fourBtn = (Button)findViewById(R.id.fourBtn);fourBtn.setOnClickListener(this);
        fiveBtn = (Button)findViewById(R.id.fiveBtn);fiveBtn.setOnClickListener(this);
        sixBtn = (Button)findViewById(R.id.sixBtn);sixBtn.setOnClickListener(this);
        subBtn = (Button)findViewById(R.id.subBtn);subBtn.setOnClickListener(this);
        powBtn = (Button)findViewById(R.id.powBtn);powBtn.setOnClickListener(this);
        oneBtn = (Button)findViewById(R.id.oneBtn);oneBtn.setOnClickListener(this);
        twoBtn = (Button)findViewById(R.id.twoBtn);twoBtn.setOnClickListener(this);
        threeBtn = (Button)findViewById(R.id.threeBtn);threeBtn.setOnClickListener(this);
        multiBtn = (Button)findViewById(R.id.multiBtn);multiBtn.setOnClickListener(this);
        sqrtBtn = (Button)findViewById(R.id.sqrtBtn);sqrtBtn.setOnClickListener(this);
        zeroBtn = (Button)findViewById(R.id.zeroBtn);zeroBtn.setOnClickListener(this);
        dotBtn = (Button)findViewById(R.id.dotBtn);dotBtn.setOnClickListener(this);
        equalBtn = (Button)findViewById(R.id.equalBtn);equalBtn.setOnClickListener(this);
        divisionBtn = (Button)findViewById(R.id.divisionBtn);divisionBtn.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.radianBtn: //弧度选项被点击
                doRadianClicked();
                break;
            case R.id.degreeBtn: //角度选项被点击
                doDegreeClicked();
                break;
            case R.id.historyBtn://查看历史保存数据
                doHistoryClicked();
                break;
            case R.id.saveBtn: //保存当前计算结果
                doSaveClicked();
                break;
            case R.id.acBtn:  //AC被点击
                doAcClicked();
                break;
            case R.id.sinBtn: //sin被点击
                doSinClicked();
                break;
            case R.id.cosBtn:  //cos被点击
                doCosClicked();
                break;
            case R.id.tanBtn:  //tan被点击
                doTanClicked();
                break;
            case R.id.lnBtn:  //log被点击
                doLnClicked();
                break;
            case R.id.backBtn:  //退格键被点击
                doBackClicked();
                break;
            case R.id.leftPaBtn:  //左括号被点击
                doLeftPaClicked();
                break;
            case R.id.sevenBtn: //7
                doSevenClicked();
                break;
            case R.id.eightBtn: //8
                doEightClicked();
                break;
            case R.id.nineBtn:  //9
                doNineClicked();
                break;
            case R.id.addBtn:  // 加号
                doAddClicked();
                break;
            case R.id.rightPaBtn: //右括号被点击
                doRightPaClicked();
                break;
            case R.id.fourBtn:  //4
                doFourClicked();
                break;
            case R.id.fiveBtn:  //5
                doFiveClicked();
                break;
            case R.id.sixBtn:  //6
                doSixClicked();
                break;
            case R.id.subBtn:   //减号
                doSubClicked();
                break;
            case R.id.powBtn:   //次方
                doPowClicked();
                break;
            case R.id.oneBtn:   //1
                doOneClicked();
                break;
            case R.id.twoBtn:  //2
                doTwoClicked();
                break;
            case R.id.threeBtn:  //3
                doThreeClicked();
                break;
            case R.id.multiBtn:  //乘号
                doMultiClicked();
                break;
            case R.id.sqrtBtn:   //根号
                doSqrtClicked();
                break;
            case R.id.zeroBtn:   //0
                doZeroClicked();
                break;
            case R.id.dotBtn:   //.
                doDotClicked();
                break;
            case R.id.equalBtn:  //=
                doEqualClicked();
                break;
            case R.id.divisionBtn:  //除号
                doDivisionClicked();
                break;
        }
    }

    private void doRadianClicked(){
        if(Arithmetic.mode==1){
            //已经是弧度制,do nothing
        }
        else{
            Arithmetic.mode=1;
            radianBtn.setChecked(true);
            degreeBtn.setChecked(false);
        }
    }
    private void doDegreeClicked(){
        //TextView t = new TextView(this);

        if(Arithmetic.mode==2){
            //已经是角度制,do nothing
        }
        else{
            Arithmetic.mode=2;
            radianBtn.setChecked(false);
            degreeBtn.setChecked(true);
        }
    }
    private void doHistoryClicked(){
        if(allowInsertHistory()==0){
            Toast.makeText(MainActivity.this,"请不要在小数点后插入数据",Toast.LENGTH_SHORT).show();
            return;
        }
        if(allowInsertHistory()==3){
            Toast.makeText(MainActivity.this,"请不要在数字后插入数据",Toast.LENGTH_SHORT).show();
            return;
        }
        if(historyDatas.size()==0){
            Toast.makeText(MainActivity.this,"目前没有存储的数据",Toast.LENGTH_SHORT).show();
            return;
        }
        final LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.historydatalayout,
                (ViewGroup) findViewById(R.id.historydialog));
        final ListView listview = (ListView)layout.findViewById(R.id.listView);
        listview.setAdapter(new ArrayAdapter<Double>(this,
                android.R.layout.simple_list_item_single_choice, historyDatas));
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        final AlertDialog dialog = new AlertDialog.Builder(this).setTitle("请选择要插入的历史数据,最下面的是最近的数据").setView(layout)
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Double d = historyDatas.get(position);
                if(allowInsertHistory()==1){
                    Element ele = new Element();
                    ele.type=ElementType.OPERATION;
                    ele.length=0;
                    ele.ope='×';
                    infix.add(ele);
                }
                Element ed = new Element();
                ed.type=ElementType.NUM;
                ed.value = historyDatas.get(position);
                ed.length = ed.value.toString().length()+2;
                infix.add(ed);
                expText.getText().insert(expText.getSelectionStart(),"("+ed.value.toString()+")");
                dialog.dismiss();
            }
        });
    }
    private void doSaveClicked(){
        String value = resultText.getText().toString();
        if(value.equals("value invalid") || value.equals("")){
            Toast.makeText(MainActivity.this,"不能存储无效值",Toast.LENGTH_SHORT).show();
        }
        else{
            Double d = Double.parseDouble(value);
            if(historyDatas.size()>0&&d.equals(historyDatas.get(historyDatas.size()-1))) {
                Toast.makeText(MainActivity.this,"该数据已经存储",Toast.LENGTH_SHORT).show();
                return;
            }
            historyDatas.add(d);
            Toast.makeText(MainActivity.this,"存储成功",Toast.LENGTH_SHORT).show();
        }
    }
    private void doAcClicked(){
        Editable editable = expText.getText();
        editable.clear();
        infix.clear();
        initVariable();
    }
    private void doSinClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        //检查是否在小数点后
        System.out.println("sin按钮被点击");
        int index = expText.getText().length();
        System.out.println("index:"+index);
        String text = expText.getText().toString();
        if(index>0 && text.charAt(index-1)=='.'){
            return;
        }
        if(startInputNum){
            startInputNum=false;
            Element ed = new Element();
            ed.type=ElementType.NUM;
            ed.value = Double.parseDouble(numBuffer.toString());
            ed.length=numBuffer.length();
            infix.add(ed);
        }
        //检查是否在数字或者右括号后面，若是，则加一个乘号
        if(index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1)) )){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        String mode;
        if(Arithmetic.mode==1)
            mode = new String("弧度");
        else
            mode = new String("角度");
        final EditText editText = new EditText(this);
        //editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this).setTitle("插入sin函数,请输入" + mode).setIcon(
                android.R.drawable.ic_dialog_info).setView(
                editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = editText.getText().toString();
                        if (value.matches("^[-+]{0,1}[0-9]+\\.{0,1}[0-9]*$")) {
                            Double d = Double.parseDouble(value);
                            if (Arithmetic.mode == 2) {
                                d = d/180*Math.PI;
                            }
                            d = Math.sin(d);
                            Element t = new Element();
                            t.length = editText.getText().length() + 5;
                            t.type = ElementType.FUNCTION;
                            t.value = d;
                            infix.add(t);
                            String s = new String("sin(" + editText.getText().toString() + ")");
                            expText.getText().insert(expText.length(), s);
                            try {
                                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                // 将mShowing变量设为true，表示关闭对话框
                                field.set(dialog, true);
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MainActivity.this,"请输入正确的数",Toast.LENGTH_SHORT).show();
                            try {
                                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                // 将mShowing变量设为false，表示不关闭对话框
                                field.set(dialog, false);
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            // 将mShowing变量设为true，表示关闭对话框
                            field.set(dialog, true);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }
    private void doCosClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        //检查是否在小数点后
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        if(index>0 && text.charAt(index-1)=='.'){
            return;
        }
        if(startInputNum){
            startInputNum=false;
            Element ed = new Element();
            ed.type=ElementType.NUM;
            ed.value = Double.parseDouble(numBuffer.toString());
            ed.length=numBuffer.length();
            infix.add(ed);
        }
        //检查是否在数字或者右括号后面，若是，则加一个乘号
        if(index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1)) )){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        String mode;
        if(Arithmetic.mode==1)
            mode = new String("弧度");
        else
            mode = new String("角度");
        final EditText editText = new EditText(this);
        //editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this).setTitle("插入cos函数,请输入" + mode).setIcon(
                android.R.drawable.ic_dialog_info).setView(
                editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = editText.getText().toString();
                        if (value.matches("^[-+]{0,1}[0-9]+\\.{0,1}[0-9]*$")) {
                            Double d = Double.parseDouble(value);
                            if (Arithmetic.mode == 2) {
                                d = d/180*Math.PI;
                            }
                            d = Math.cos(d);
                            Element t = new Element();
                            t.length = editText.getText().length() + 5;
                            t.type = ElementType.FUNCTION;
                            t.value = d;
                            infix.add(t);
                            String s = new String("cos(" + editText.getText().toString() + ")");
                            expText.getText().insert(expText.length(), s);
                            try {
                                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                // 将mShowing变量设为true，表示关闭对话框
                                field.set(dialog, true);
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MainActivity.this,"请输入正确的数",Toast.LENGTH_SHORT).show();
                            try {
                                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                // 将mShowing变量设为false，表示不关闭对话框
                                field.set(dialog, false);
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            // 将mShowing变量设为true，表示关闭对话框
                            field.set(dialog, true);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }
    private void doTanClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        //检查是否在小数点后
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        if(index>0 && text.charAt(index-1)=='.'){
            return;
        }
        if(startInputNum){
            startInputNum=false;
            Element ed = new Element();
            ed.type=ElementType.NUM;
            ed.value = Double.parseDouble(numBuffer.toString());
            ed.length=numBuffer.length();
            infix.add(ed);
        }
        //检查是否在数字或者右括号后面，若是，则加一个乘号
        if(index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1)) )){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        String mode;
        if(Arithmetic.mode==1)
            mode = new String("弧度");
        else
            mode = new String("角度");
        final EditText editText = new EditText(this);
        //editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this).setTitle("插入tan函数,请输入" + mode).setIcon(
                android.R.drawable.ic_dialog_info).setView(
                editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = editText.getText().toString();
                        if (value.matches("^[-+]{0,1}[0-9]+\\.{0,1}[0-9]*$")) {
                            Double d = Double.parseDouble(value);
                            if (Arithmetic.mode == 2) {
                                d = d/180*Math.PI;
                            }
                            d = Math.tan(d);
                            Element t = new Element();
                            t.length = editText.getText().length() + 5;
                            t.type = ElementType.FUNCTION;
                            t.value = d;
                            infix.add(t);
                            String s = new String("tan(" + editText.getText().toString() + ")");
                            expText.getText().insert(expText.length(), s);
                            try {
                                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                // 将mShowing变量设为true，表示关闭对话框
                                field.set(dialog, true);
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MainActivity.this,"请输入正确的数",Toast.LENGTH_SHORT).show();
                            try {
                                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                // 将mShowing变量设为false，表示不关闭对话框
                                field.set(dialog, false);
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            // 将mShowing变量设为true，表示关闭对话框
                            field.set(dialog, true);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }
    private void doLnClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        //检查是否在小数点后
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        if(index>0 && text.charAt(index-1)=='.'){
            return;
        }
        if(startInputNum){
            startInputNum=false;
            Element ed = new Element();
            ed.type=ElementType.NUM;
            ed.value = Double.parseDouble(numBuffer.toString());
            ed.length=numBuffer.length();
            infix.add(ed);
        }
        //检查是否在数字或者右括号后面，若是，则加一个乘号
        if(index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1)) )){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        final EditText editText = new EditText(this);
        //editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this).setTitle("插入ln函数,请输入数值").setIcon(
                android.R.drawable.ic_dialog_info).setView(
                editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = editText.getText().toString();
                        if (value.matches("^[0-9]+\\.{0,1}[0-9]*$")) {
                            Double d = Double.parseDouble(value);
                            d = Math.log(d);
                            Element t = new Element();
                            t.length = editText.getText().length() + 4;
                            t.type = ElementType.FUNCTION;
                            t.value = d;
                            infix.add(t);
                            String s = new String("ln(" + editText.getText().toString() + ")");
                            expText.getText().insert(expText.length(), s);
                            try {
                                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                // 将mShowing变量设为true，表示关闭对话框
                                field.set(dialog, true);
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MainActivity.this,"请输入正数",Toast.LENGTH_SHORT).show();
                            try {
                                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                // 将mShowing变量设为false，表示不关闭对话框
                                field.set(dialog, false);
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            // 将mShowing变量设为true，表示关闭对话框
                            field.set(dialog, true);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }
    private void doBackClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        Editable editable = expText.getText();
        if(infix.size()>0){
            if(!startInputNum)
            {
                Element toBeDelete=infix.get(infix.size()-1);
                if(toBeDelete.length!=0) {
                    int length = toBeDelete.length;
                    int delStart = index - length;
                    if (delStart < 0)
                        delStart = 0;
                    editable.delete(delStart, index);
                    infix.remove(infix.size() - 1);
                    if (editable.length() == 0) {
                        initVariable();
                    }
                }
                else{
                    infix.remove(infix.size()-1);
                    doBackClicked();
                }
            }
            else{
                numBuffer.deleteCharAt(numBuffer.length()-1);
                editable.delete(index-1,index);
                if(numBuffer.length()==0)
                    startInputNum=false;
            }
        }
        else{
            if(startInputNum){
                numBuffer.deleteCharAt(numBuffer.length()-1);
                editable.delete(index-1,index);
                if(numBuffer.length()==0)
                    startInputNum=false;
            }
        }
    }
    private void doLeftPaClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        //检查是否在小数点后
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        if(index>0 && text.charAt(index-1)=='.'){
            return;
        }
        //检查是否在数字或者函数右括号后面，若是，则加一个乘号
        if(index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1)))){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        if(startInputNum) {
            startInputNum = false;
        }
        expText.getText().insert(expText.getSelectionStart(),"(");
        Element ele = new Element();
        ele.type=ElementType.OPERATION;
        ele.length=1;
        ele.ope='(';
        infix.add(ele);
    }
    private void doSevenClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        //检查是否在右括号后面，若是，则加一个乘号
        if(!startInputNum && (index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1))))){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        if(!startInputNum){
            numBuffer.delete(0,numBuffer.length());
            startInputNum=true;
        }
        numBuffer.append("7");
        expText.getText().insert(expText.getSelectionStart(),"7");
    }
    private void doEightClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        //检查是否在右括号后面，若是，则加一个乘号
        if(!startInputNum && (index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1))))){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        if(!startInputNum){
            numBuffer.delete(0,numBuffer.length());
            startInputNum=true;
        }
        numBuffer.append("8");
        expText.getText().insert(expText.getSelectionStart(),"8");
    }
    private void doNineClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        //检查是否在右括号后面，若是，则加一个乘号
        if(!startInputNum && (index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1))))){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        if(!startInputNum){
            numBuffer.delete(0,numBuffer.length());
            startInputNum=true;
        }
        numBuffer.append("9");
        expText.getText().insert(expText.getSelectionStart(),"9");
    }
    private void doAddClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        //检查是否在小数点或者运算符后
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        if(index>0 && (text.charAt(index-1)=='.' || isOpe(text.charAt(index-1)))){
            return;
        }
        if(startInputNum){
            startInputNum=false;
            Double d = Double.parseDouble(numBuffer.toString());
            Element ed = new Element();
            ed.type=ElementType.NUM;
            ed.length = numBuffer.length();
            ed.value=d;
            infix.add(ed);
        }
        if(index==0 || text.charAt(index-1)=='('){
            //添加一个0
            Element ele = new Element();
            ele.type=ElementType.NUM;
            ele.length=0;
            ele.value=0.0;
            infix.add(ele);
        }
        Element ea = new Element();
        ea.type=ElementType.OPERATION;
        ea.ope='+';
        ea.length=1;
        infix.add(ea);
        expText.getText().insert(expText.length(),"+");
    }
    private void doRightPaClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        //检查是否在小数点或者运算符后
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        if(index==0 || (index>0 && (text.charAt(index-1)=='.' || isOpe(text.charAt(index-1))))){
            return;
        }
        if(startInputNum){
            startInputNum=false;
            Double d = Double.parseDouble(numBuffer.toString());
            Element ed = new Element();
            ed.type=ElementType.NUM;
            ed.length = numBuffer.length();
            ed.value=d;
            infix.add(ed);
        }
        Element er = new Element();
        er.type=ElementType.OPERATION;
        er.ope=')';
        er.length=1;
        infix.add(er);
        expText.getText().insert(expText.length(),")");
    }
    private void doFourClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        //检查是否在右括号后面，若是，则加一个乘号
        if(!startInputNum && (index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1))))){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        if(!startInputNum){
            numBuffer.delete(0,numBuffer.length());
            startInputNum=true;
        }
        numBuffer.append("4");
        expText.getText().insert(expText.getSelectionStart(),"4");
    }
    private void doFiveClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        //检查是否在右括号后面，若是，则加一个乘号
        if(!startInputNum && (index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1))))){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        if(!startInputNum){
            numBuffer.delete(0,numBuffer.length());
            startInputNum=true;
        }
        numBuffer.append("5");
        expText.getText().insert(expText.getSelectionStart(),"5");
    }
    private void doSixClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        //检查是否在右括号后面，若是，则加一个乘号
        if(!startInputNum && (index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1))))){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        if(!startInputNum){
            numBuffer.delete(0,numBuffer.length());
            startInputNum=true;
        }
        numBuffer.append("6");
        expText.getText().insert(expText.getSelectionStart(),"6");
    }
    private void doSubClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        //检查是否在小数点或者运算符后
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        if(index>0 && (text.charAt(index-1)=='.' || isOpe(text.charAt(index-1)))){
            return;
        }
        if(startInputNum){
            startInputNum=false;
            Double d = Double.parseDouble(numBuffer.toString());
            Element ed = new Element();
            ed.type=ElementType.NUM;
            ed.length = numBuffer.length();
            ed.value=d;
            infix.add(ed);
        }
        if(index==0 || text.charAt(index-1)=='('){
            //添加一个0
            Element ele = new Element();
            ele.type=ElementType.NUM;
            ele.length=0;
            ele.value=0.0;
            infix.add(ele);
        }
        Element ea = new Element();
        ea.type=ElementType.OPERATION;
        ea.ope='-';
        ea.length=1;
        infix.add(ea);
        expText.getText().insert(expText.length(),"-");
    }
    private void doPowClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        if(index>0 && text.charAt(index-1)=='.'){
            return;
        }
        if(startInputNum){
            startInputNum=false;
            Element ed = new Element();
            ed.type=ElementType.NUM;
            ed.value = Double.parseDouble(numBuffer.toString());
            ed.length=numBuffer.length();
            infix.add(ed);
        }
        //检查是否在数字或者右括号后面，若是，则加一个乘号
        if(index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1)) )){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.powdialoglayout,
                (ViewGroup) findViewById(R.id.powdialog));

        final EditText powXtext = (EditText)layout.findViewById(R.id.powXtext);
        final EditText powYtext = (EditText)layout.findViewById(R.id.powYtext);

        new AlertDialog.Builder(this).setTitle("幂函数,X^Y,请分别输入X和Y").setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String valueX = powXtext.getText().toString();
                        String valueY = powYtext.getText().toString();
                        if (valueX.matches("^[-+]{0,1}[0-9]+\\.{0,1}[0-9]*$") && valueY.matches("^[-+]{0,1}[0-9]+\\.{0,1}[0-9]*$")) {
                            Double dX = Double.parseDouble(valueX);
                            Double dY = Double.parseDouble(valueY);
                            Double d = Math.pow(dX,dY);
                            Element t = new Element();
                            t.length = powXtext.getText().length()+powYtext.getText().length()+3;
                            t.type = ElementType.FUNCTION;
                            t.value = d;
                            infix.add(t);
                            String s = new String("("+powXtext.getText().toString()+"^"+powYtext.getText().toString()+")");
                            expText.getText().insert(expText.length(), s);
                            try {
                                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                // 将mShowing变量设为true，表示关闭对话框
                                field.set(dialog, true);
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MainActivity.this,"请输入正确的X和Y",Toast.LENGTH_SHORT).show();
                            try {
                                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                // 将mShowing变量设为false，表示不关闭对话框
                                field.set(dialog, false);
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            // 将mShowing变量设为true，表示关闭对话框
                            field.set(dialog, true);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }
    private void doOneClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        //检查是否在右括号后面，若是，则加一个乘号
        if(!startInputNum && (index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1))))){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        if(!startInputNum){
            numBuffer.delete(0,numBuffer.length());
            startInputNum=true;
        }
        numBuffer.append("1");
        expText.getText().insert(expText.getSelectionStart(),"1");
    }
    private void doTwoClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        //检查是否在右括号后面，若是，则加一个乘号
        if(!startInputNum && (index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1))))){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        if(!startInputNum){
            numBuffer.delete(0,numBuffer.length());
            startInputNum=true;
        }
        numBuffer.append("2");
        expText.getText().insert(expText.getSelectionStart(),"2");
    }
    private void doThreeClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        //检查是否在右括号后面，若是，则加一个乘号
        if(!startInputNum && (index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1))))){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        if(!startInputNum){
            numBuffer.delete(0,numBuffer.length());
            startInputNum=true;
        }
        numBuffer.append("3");
        expText.getText().insert(expText.getSelectionStart(),"3");
    }
    private void doMultiClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        if(index==0 || (index>0 && (text.charAt(index-1)=='.' || isOpe(text.charAt(index-1)) ||
            text.charAt(index-1)=='(' ))   ){
            return;
        }
        if(startInputNum){
            startInputNum=false;
            Double d = Double.parseDouble(numBuffer.toString());
            Element ed = new Element();
            ed.type=ElementType.NUM;
            ed.length = numBuffer.length();
            ed.value=d;
            infix.add(ed);
        }
        Element em = new Element();
        em.type=ElementType.OPERATION;
        em.length=1;
        em.ope='×';
        infix.add(em);
        expText.getText().insert(expText.getSelectionStart(),"×");
    }
    private void doSqrtClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        if(index>0 && text.charAt(index-1)=='.'){
            return;
        }
        if(startInputNum){
            startInputNum=false;
            Element ed = new Element();
            ed.type=ElementType.NUM;
            ed.value = Double.parseDouble(numBuffer.toString());
            ed.length=numBuffer.length();
            infix.add(ed);
        }
        //检查是否在数字或者右括号后面，若是，则加一个乘号
        if(index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1)) )){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        new AlertDialog.Builder(this).setTitle("插入平方根函数,请输入数值").setIcon(
                android.R.drawable.ic_dialog_info).setView(
                editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = editText.getText().toString();
                        if (value.matches("^[0-9]+\\.{0,1}[0-9]*$")) {
                            Double d = Double.parseDouble(value);
                            d = Math.sqrt(d);
                            Element t = new Element();
                            t.length = editText.getText().length() + 3;
                            t.type = ElementType.FUNCTION;
                            t.value = d;
                            infix.add(t);
                            String s = new String("√(" + editText.getText().toString() + ")");
                            expText.getText().insert(expText.length(), s);
                            try {
                                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                // 将mShowing变量设为true，表示关闭对话框
                                field.set(dialog, true);
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MainActivity.this,"请输入正数",Toast.LENGTH_SHORT).show();
                            try {
                                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                // 将mShowing变量设为false，表示不关闭对话框
                                field.set(dialog, false);
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            // 将mShowing变量设为true，表示关闭对话框
                            field.set(dialog, true);
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
    }
    private void doZeroClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        //检查是否在右括号后面，若是，则加一个乘号
        if(!startInputNum && (index>0 && (text.charAt(index-1)==')'||Character.isDigit(text.charAt(index-1))))){
            Element temp = new Element();
            temp.type=ElementType.OPERATION;
            temp.length=0;
            temp.ope='×';
            infix.add(temp);
        }
        if(!startInputNum){
            numBuffer.delete(0,numBuffer.length());
            startInputNum=true;
        }
        numBuffer.append("0");
        expText.getText().insert(expText.getSelectionStart(),"0");
    }
    private void doDotClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        if(index==0 || ( index>0 && (text.charAt(index-1)=='.' || isOpe(text.charAt(index-1)) ||
                text.charAt(index-1)=='(' || text.charAt(index-1)==')') )  ){
            return;
        }
        if(startInputNum){
            numBuffer.append(".");
            expText.getText().insert(expText.getSelectionStart(),".");
        }
    }
    private void doEqualClicked(){
        if(startInputNum){
            startInputNum=false;
            Double d = Double.parseDouble(numBuffer.toString());
            Element ed = new Element();
            ed.type=ElementType.NUM;
            ed.length = numBuffer.length();
            ed.value=d;
            infix.add(ed);
        }
        String result = Arithmetic.getResult(infix);
        if(result.equals("表达式输入有误")){
            Toast.makeText(MainActivity.this,"表达式输入有误",Toast.LENGTH_SHORT).show();
        }
        else
            resultText.setText(result);
        equalfinished=true;
    }
    private void doDivisionClicked(){
        if(equalfinished) {
            doAcClicked();
            equalfinished = false;
        }
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        if(index==0 || (index>0 && (text.charAt(index-1)=='.' || isOpe(text.charAt(index-1)) ||
                text.charAt(index-1)=='(' ))   ){
            return;
        }
        if(startInputNum){
            startInputNum=false;
            Double d = Double.parseDouble(numBuffer.toString());
            Element ed = new Element();
            ed.type=ElementType.NUM;
            ed.length = numBuffer.length();
            ed.value=d;
            infix.add(ed);
        }
        Element em = new Element();
        em.type=ElementType.OPERATION;
        em.length=1;
        em.ope='/';
        infix.add(em);
        expText.getText().insert(expText.getSelectionStart(),"/");
    }
    private boolean isOpe(char c){
        if(c=='+' || c=='-' || c=='×' || c=='/')
            return true;
        else
            return false;
    }
    private int allowInsertHistory(){
        if(startInputNum)
            return 3;
        int index = expText.getSelectionStart();
        String text = expText.getText().toString();
        if(index>0 && text.charAt(index-1)=='.'){
            return 0;
        }
        else if(index>0 && Character.isDigit(text.charAt(index-1)))
            return 3;
        else if(index>0 && text.charAt(index-1)==')')
            return 1;
        else
            return 2;
    }
}
