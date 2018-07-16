package com.example.dezvezesdez.getbus_android;



import android.app.FragmentTransaction;
import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;

import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.Utils;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;

import org.joda.time.DateTime;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Activity_Bilheteira extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    //for payPal function
    private static final String CONFIG_CLIENT_ID = "your client id";

    private ArrayList array_list_destinos_chegadas;

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private LinearLayout ll_volta;

    private ArrayList<String> cities;
    private String[] num;
    private ArrayAdapter<String> adapter_cities;
    private ArrayAdapter<String> adapter_num;

    private String ida_e_volta;
    private String ida;
    private String dia;
    private String mes;
    private String ano;
    private String hora;
    private String minuto;
    private String viagem_id;
    public static String price;
    public static String id_random;

    private Spinner sp_partida;
    private Spinner sp_ir_ate;

    private Spinner sp_bilhetes_adultos;
    private Spinner sp_bilhetes_criancas;

    private TextView tx_from_time_hora_minute; // to show time
    private TextView tx_from_dia;
    private TextView tx_from_mes;
    private TextView tx_from_ano;
    private TextView tx_to_time_hora_minute; // to show time
    private TextView tx_to_dia;
    private TextView tx_to_mes;
    private TextView tx_to_ano;

    private Switch switch_button;
    private Button bt_comprar;
    private Button bt_calcular;
    private TextView tv_price_pay;

    private CalendarDatePickerDialogFragment.OnDateSetListener from_dateListener;
    private RadialTimePickerDialogFragment.OnTimeSetListener from_timeListener;
    private CalendarDatePickerDialogFragment.OnDateSetListener to_dateListener;
    private RadialTimePickerDialogFragment.OnTimeSetListener to_timeListener;

    boolean flag_ida_e_volta;

    private int mStackLevel;

    //final Calendar now = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilheteira);

        mStackLevel=0;



        switch_button = (Switch) findViewById(R.id.switch_button);

        ll_volta = (LinearLayout) findViewById(R.id.ll_volta);

        sp_partida = (Spinner) findViewById(R.id.sp_partida);
        sp_ir_ate = (Spinner) findViewById(R.id.sp_ir_ate);
        sp_bilhetes_adultos = (Spinner) findViewById(R.id.sp_bilhetes_adultos);
        sp_bilhetes_criancas = (Spinner) findViewById(R.id.sp_bilhetes_criancas);

        String Title = getString(R.string.app_name);
        ida_e_volta = getString(R.string.tv_id_e_volta);
        ida = getString(R.string.tv_apenas_ida);

        cities =  new ArrayList(Arrays.asList(getResources().getStringArray(R.array.string_array_cities)));

        num = getResources().getStringArray(R.array.string_array_num_adultos_crianças);

        tv_price_pay = (TextView) findViewById(R.id.tv_price_pay);

        tx_from_time_hora_minute = (TextView) findViewById(R.id.tx_from_time_hora_minute);
        tx_from_dia = (TextView) findViewById(R.id.tx_from_dia);
        tx_from_mes = (TextView) findViewById(R.id.tx_from_mes);
        tx_from_ano = (TextView) findViewById(R.id.tx_from_ano);

        tx_to_time_hora_minute = (TextView) findViewById(R.id.tx_to_time_hora_minute);
        tx_to_dia = (TextView) findViewById(R.id.tx_to_dia);
        tx_to_mes = (TextView) findViewById(R.id.tx_to_mes);
        tx_to_ano = (TextView) findViewById(R.id.tx_to_ano);

        tx_from_time_hora_minute.setVisibility(View.GONE);
        tx_from_dia.setVisibility(View.GONE);
        tx_from_mes.setVisibility(View.GONE);
        tx_from_ano.setVisibility(View.GONE);

        tx_to_time_hora_minute.setVisibility(View.GONE);
        tx_to_dia.setVisibility(View.GONE);
        tx_to_mes.setVisibility(View.GONE);
        tx_to_ano.setVisibility(View.GONE);

        dia = getString(R.string.dia);
        mes = getString(R.string.mes);
        ano = getString(R.string.ano);
        hora = getString(R.string.hora);
        minuto = getString(R.string.minuto);

        UI_menu_inflater();


        ButtonSwicth();
        SpinnersPopulate();
        DataAndTime_Pickers();
        ShowPrice();
        ButtonPay();







    }

    private void UI_menu_inflater(){

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setIcon(null);

        appBarLayout.addOnOffsetChangedListener(this);

        // collapsingToolbarLayout.setTitle(Title);
        collapsingToolbarLayout.setTitle(null);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_bilheteira, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_pt) {
           // Toast.makeText(this, "Portugues", Toast.LENGTH_LONG).show();
            Helper_App.setLocale(this, "pt");
            return true;
        }

        if (id == R.id.action_eng) {
           // Toast.makeText(this, "Ingles", Toast.LENGTH_LONG).show();
            Helper_App.setLocale(this, "en");
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        if (verticalOffset == 0) {
            // Collapsed - ou seja aberto
            getSupportActionBar().setIcon(null);

        } else {
            // Not collapsed - quando esta fechado
            getSupportActionBar().setIcon(R.mipmap.ic_simples_getbus);


        }

    }

    public void ButtonSwicth() {

        //inicializar com o botao ligado
        switch_button.setChecked(true);
        flag_ida_e_volta = true;

        switch_button.setTextOn(ida_e_volta);
        switch_button.setTextOff(ida);

        switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ll_volta.setVisibility(View.VISIBLE);
                    flag_ida_e_volta = true;
                    //Toast.makeText(Activity_Bilheteira.this, "ida e volta", Toast.LENGTH_LONG).show();
                } else {
                    ll_volta.setVisibility(View.GONE);
                    flag_ida_e_volta = false;
                    //Toast.makeText(Activity_Bilheteira.this, " apenas ida ", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void CalculatePay() {

        String result = null;

        String ponto_de_partida = null;
        String ponto_de_chegada = null;

        String bilhetes_adultos = null;
        String bilhetes_criancas = null;

        ponto_de_partida = sp_partida.getSelectedItem().toString();
        ponto_de_chegada = sp_ir_ate.getSelectedItem().toString();
        bilhetes_adultos = sp_bilhetes_adultos.getSelectedItem().toString();
        bilhetes_criancas = sp_bilhetes_criancas.getSelectedItem().toString();


        if (!ponto_de_partida.equalsIgnoreCase(ponto_de_chegada)) {

            result = Helper_APi.GetPriceFromAPi(bilhetes_adultos, bilhetes_criancas, flag_ida_e_volta);

        } else {
            String mensagem_erro = getString(R.string.mensagem_erro);
            Toast.makeText(Activity_Bilheteira.this, mensagem_erro, Toast.LENGTH_LONG).show();
        }

        if (result != null) {
            result = result + " " + "€";
            tv_price_pay.setText(result);

        } else {
            tv_price_pay.setText("€");
        }

    }

    public void ButtonPay() {

        bt_comprar = (Button) findViewById(R.id.bt_comprar);

        bt_comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                price = tv_price_pay.getText().toString();
                id_random = new BigInteger(130, new SecureRandom()).toString(32);

                if(!price.equals("€") && !price.equals(" €") && !price.equals("0 €")){

                  /*
                    // Intent i = new Intent(Activity_Bilheteira.this, Activity_Pagar.class);
                    Intent i = new Intent(Activity_Bilheteira.this, Activity_Formulario.class);
                    i.putExtra("tag_price", price);
                    i.putExtra("tag_id", id_random);
                    startActivity(i);
                  */

                    FragmentManager fm = getSupportFragmentManager();
                    DialogFragment_seguimento_compra dfsc = new DialogFragment_seguimento_compra();
                    dfsc.show(fm, "tag");





                }else{
                    CalculatePay();
                }

            }
        });


    }

    public void ShowPrice() {

        bt_calcular = (Button) findViewById(R.id.bt_calcular);

        bt_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculatePay();
            }
        });


    }

    public void SpinnersPopulate() {

        //proximo passo é popular o spinner com este array list

        array_list_destinos_chegadas= null;

        try {

            Intent i = getIntent();
            array_list_destinos_chegadas = new ArrayList(i.getExtras().getIntegerArrayList("destinos_chegadas"));

        } catch (Exception e) {
            e.printStackTrace();
            array_list_destinos_chegadas = cities;
        }




        //adapter_cities = new ArrayAdapter<>(this, R.layout.my_simple_spinner_item, cities);
        adapter_cities = new ArrayAdapter<>(this, R.layout.my_simple_spinner_item, array_list_destinos_chegadas);
        adapter_num = new ArrayAdapter<>(this, R.layout.my_simple_spinner_item, num);

        adapter_cities.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        adapter_num.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        sp_partida.setAdapter(adapter_cities);
        sp_partida.setSelection(0);
        sp_ir_ate.setAdapter(adapter_cities);
        sp_ir_ate.setSelection(1);
        sp_bilhetes_adultos.setAdapter(adapter_num);
        sp_bilhetes_criancas.setAdapter(adapter_num);

    }

    public void DataAndTime_Pickers() {

        // ver link
        //      https://github.com/code-troopers/android-betterpickers/blob/master/sample/src/main/java/com/codetroopers/betterpickers/sample/activity/radialtimepicker/SampleRadialTimeThemeCustom.java


        Button bt_from_timer = (Button) findViewById(R.id.bt_from_timer);
        Button bt_from_date = (Button) findViewById(R.id.bt_from_date);

        Button bt_to_timer = (Button) findViewById(R.id.bt_to_timer);
        Button bt_to_date = (Button) findViewById(R.id.bt_to_date);

        assert bt_from_date != null;
        bt_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                from_dateListener = new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

                        String from_year = String.valueOf(year);
                        String from_monthOfYear = String.valueOf(monthOfYear);
                        String from_dayOfMonth = String.valueOf(dayOfMonth);

                        // String date = "Dia:"+ from_dayOfMonth+ ", Mês:" + from_monthOfYear + ", Ano:" +  from_year;
                        //String date = dia + from_dayOfMonth+ mes + from_monthOfYear + ano +  from_year;

                        tx_from_dia.setVisibility(View.VISIBLE);
                        tx_from_mes.setVisibility(View.VISIBLE);
                        tx_from_ano.setVisibility(View.VISIBLE);


                        String from_monthOfYear_clean = Helper_App.NumberToMonth(Activity_Bilheteira.this, monthOfYear);

                        tx_from_dia.setText(dia + " " + from_dayOfMonth);
                        tx_from_mes.setText(mes + " " + from_monthOfYear_clean);
                        tx_from_ano.setText(ano + " " + from_year);
                    }
                };

                DateTime dateNow = DateTime.now();
                DateTime towDaysAgo = dateNow.minusDays(2);
                MonthAdapter.CalendarDay minDate = new MonthAdapter.CalendarDay(dateNow.getYear(), dateNow.getMonthOfYear() - 2, dateNow.getDayOfMonth());
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay(dateNow.getYear(), dateNow.getMonthOfYear(), dateNow.getDayOfMonth());

                // Initialize disabled days list
                // Disabled days are located at a formatted location in the format "yyyyMMdd"
                SparseArray<MonthAdapter.CalendarDay> disabledDays = new SparseArray<>();
                Calendar startCal = Calendar.getInstance();
                startCal.setTimeInMillis(minDate.getDateInMillis());
                Calendar endCal = Calendar.getInstance();
                endCal.setTimeInMillis(maxDate.getDateInMillis());
                // Add all weekend days within range to disabled days
                while (startCal.before(endCal)) {
                    if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                            || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        int key = Utils.formatDisabledDayForKey(startCal.get(Calendar.YEAR),
                                startCal.get(Calendar.MONTH), startCal.get(Calendar.DAY_OF_MONTH));
                        disabledDays.put(key, new MonthAdapter.CalendarDay(startCal));
                    }
                    startCal.add(Calendar.DATE, 1);
                }


                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(from_dateListener)
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(towDaysAgo.getYear(), towDaysAgo.getMonthOfYear() - 1, towDaysAgo.getDayOfMonth())
                        .setDateRange(minDate, maxDate)
                                //.setDateRange(null, maxDate)
                                //.setDateRange(minDate, null)
                        .setDisabledDays(disabledDays)
                        .setCancelText("cancel")
                                //.setCancelText(getString(R.string.button_label_custom_cancel))
                        .setDoneText("ok")
                                //.setDoneText(getString(R.string.button_label_custom_ok))
                        .setThemeCustom(R.style.MyCustomBetterPickerTheme);
                cdp.show(getSupportFragmentManager(), "Tag");


            }
        });


        assert bt_from_timer != null;
        bt_from_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                from_timeListener = new RadialTimePickerDialogFragment.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {

                        String from_hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                        String from_minuteString = minute < 10 ? "0" + minute : "" + minute;

                        //String time = "You picked the following time: From - " + hourString + "h" + minuteString;
                        String time = hora + " " + from_hourString + ",  " + minuto + " " + from_minuteString;

                        tx_from_time_hora_minute.setVisibility(View.VISIBLE);
                        tx_from_time_hora_minute.setText(time);

                    }
                };


                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(from_timeListener)
                                //.setFutureMinutesLimit(60)
                                //.setPastMinutesLimit(60)
                                //.setValidateDateTime(Calendar.getInstance())
                                //.setPickerDate(Calendar.getInstance())
                        .setCancelText("cancel")
                                //.setForced12hFormat()
                        .setForced24hFormat()
                                //.setCancelText(getString(R.string.button_label_custom_cancel))
                        .setDoneText("ok")
                                //.setDoneText(getString(R.string.button_label_custom_ok))
                        .setThemeCustom(R.style.MyCustomBetterPickersRadialTimePickerDialog);
                rtpd.show(getSupportFragmentManager(), "Tag");


            }
        });


        assert bt_to_date != null;
        bt_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                to_dateListener = new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

                        String to_year = String.valueOf(year);
                        String to_monthOfYear = String.valueOf(monthOfYear);
                        String to_dayOfMonth = String.valueOf(dayOfMonth);

                        // String date = "Dia:"+ from_dayOfMonth+ ", Mês:" + from_monthOfYear + ", Ano:" +  from_year;
                        //String date = dia + from_dayOfMonth+ mes + from_monthOfYear + ano +  from_year;

                        tx_to_dia.setVisibility(View.VISIBLE);
                        tx_to_mes.setVisibility(View.VISIBLE);
                        tx_to_ano.setVisibility(View.VISIBLE);

                        tx_to_dia.setText(dia + " " + to_dayOfMonth);
                        tx_to_mes.setText(mes + " " + to_monthOfYear);
                        tx_to_ano.setText(ano + " " + to_year);
                    }
                };

                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(to_dateListener)
                        .setCancelText("cancel")
                                //.setCancelText(getString(R.string.button_label_custom_cancel))
                        .setDoneText("ok")
                                //.setDoneText(getString(R.string.button_label_custom_ok))
                        .setThemeCustom(R.style.MyCustomBetterPickerTheme);
                cdp.show(getSupportFragmentManager(), "Tag");


            }
        });


        assert bt_to_timer != null;
        bt_to_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                to_timeListener = new RadialTimePickerDialogFragment.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
                        String to_hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                        String to_minuteString = minute < 10 ? "0" + minute : "" + minute;

                        //String time = "You picked the following time: From - " + hourString + "h" + minuteString;
                        String time = hora + " " + to_hourString + ",  " + minuto + " " + to_minuteString;

                        tx_to_time_hora_minute.setVisibility(View.VISIBLE);
                        tx_to_time_hora_minute.setText(time);
                    }
                };

                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(to_timeListener)
                        .setCancelText("cancel")
                                //.setCancelText(getString(R.string.button_label_custom_cancel))
                        .setDoneText("ok")
                                //.setDoneText(getString(R.string.button_label_custom_ok))
                        .setThemeCustom(R.style.MyCustomBetterPickersRadialTimePickerDialog);
                rtpd.show(getSupportFragmentManager(), "Tag");


            }
        });


    }




}
