package bus.track.bustracker;
/**
 *		Created on: 21-09-2016  
 *      Author: Harish Mohan
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Checkroute extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    Button jcschedule;
    Spinner jsrc,jdst;
    List<String> categories = new ArrayList<String>();
    List<String> Guduvancherry = new ArrayList<String>();
    List<String> Vandalur = new ArrayList<String>();
    List<String> Tambaram = new ArrayList<String>();
    List<String> Vadapalani = new ArrayList<String>();
    List<String> Ambattur = new ArrayList<String>();
    List<String> Avadi = new ArrayList<String>();
    List<String> Perambur = new ArrayList<String>();
    List<String> Egmore = new ArrayList<String>();
    List<String> Madhavaram = new ArrayList<String>();
    List<String> Adyar = new ArrayList<String>();
    List<String> Porur = new ArrayList<String>();
    List<String> Anna_Nagar = new ArrayList<String>();
    List<String> Koyambedu = new ArrayList<String>();
    List<String> Mangadu = new ArrayList<String>();
    List<String> Thiruvotriyur = new ArrayList<String>();
    List<String> Thiruverkadu = new ArrayList<String>();
    //String srk="Guduvancherry",dsk="Tambaram";
    int sk=0,dk=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkroute);
        getSupportActionBar();
        jcschedule=(Button)findViewById(R.id.cschedule);
       jcschedule.setOnClickListener(this);
        jsrc=(Spinner)findViewById(R.id.cspinsrc);
        jdst=(Spinner)findViewById(R.id.cspindst);

        categories.add("Guduvancherry"); categories.add("Vandalur"); categories.add("Tambaram"); categories.add("Vadapalani");  categories.add("Ambattur");
        categories.add("Avadi"); categories.add("Perambur"); categories.add("Egmore"); categories.add("Madhavaram"); categories.add("Adyar");
        categories.add("Porur"); categories.add("Anna Nagar"); categories.add("Koyambedu"); categories.add("Mangadu"); categories.add("Thiruvotriyur");
        categories.add("Thiruverkadu");

        Guduvancherry.addAll(Arrays.asList("21", "118", "500", "170K", "18L", "E18", "G18", "G70", "562", "55B", "P21", "58G", "555", "170C", "55K", "555N", "527", "M27", "554A", "17H"));
        Vandalur.addAll(Arrays.asList("21", "118", "500", "170K", "18L", "E18", "G18", "114", "P21", "G70", "70A", "170L", "592A", "170A", "170T", "554A", "555", "515", "517", "566", "500C", "P66", "01A", "49R","17H"));
        Tambaram.addAll(Arrays.asList("21","118","500","170K","18L","E18","G18","114","P21","G70","70A","170L","592A","170A","170T","554A","555","515","517","566","500C","P66","01A","49R","555","517","515","566","510","500C","P66","01B","17H"));
        Vadapalani.addAll(Arrays.asList("G70","170K","500","M27","27C","114","17","17E","17K","17C","17B","170A","170T","05E","17M","37E","49B","25G","46","17A","M12B","01B","17H"));
        Ambattur.addAll(Arrays.asList("170K","562","70A","170L","562B","536","62","592A","40A","40","57","157","47D","M70A","M70E","57A","34","20K","17H"));
        Avadi.addAll(Arrays.asList("55B","21","118","500","170K","G70","70A","70","266","170P","150","170","562B","536","62","563","592A","40","40A","47D","A47","266","54","120","46","M70A","M70E","562","150","17H"));
        Perambur.addAll(Arrays.asList("592A","114","562","563","23A","536","19G","17H"));
        Egmore.addAll(Arrays.asList("E18","G18","P21","500","M127B","170","118A","17","17C","17E","17K","562","40","40A","23A","592A","37E","57","23C","29L","15","01","10A","17B","17H"));
        Madhavaram.addAll(Arrays.asList("55B","58G","170A","170T","17B","562","57","157","536","562B","592A","37E","17H"));
        Adyar.addAll(Arrays.asList("P21","555","05E","562","47D","A47","592A","19G","23C","29L","P49","23M","54M","549","49","17H"));
        Porur.addAll(Arrays.asList("500","554A","166","17B","501","525","538","17C","17M","25G","37E","49B","562","266","54","592A","54B","518","17H"));
        Anna_Nagar.addAll(Arrays.asList("555","515","517","566","54B","M27","46","46C","518","562","120","46","592A","17","15","01","10A","17H"));
        Koyambedu.addAll(Arrays.asList("170K","170C","500","500C","21","114","118","515","517","170A","170K","170L","E18","G18","510","27C","17A","M12B","M70A","M70E","562","P49","23M","46","46C","17H"));
        Mangadu.addAll(Arrays.asList("55K","P66","66","266","17B","49B","57A","562","592A","37E","562B","54M","549","17H"));
        Thiruvotriyur.addAll(Arrays.asList("555N","527","515","517","01A","01B","17","17B","562","34","592A","37E","17H"));
        Thiruverkadu.addAll(Arrays.asList("M27","170K","49R","500","E18","G18","170","27C","562","20K","150","592A","49","17H"));

        // if(categories.contains("Arakonam"))
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinneritem, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jsrc.setAdapter(dataAdapter);
        jsrc.setOnItemSelectedListener(this);
        jdst.setAdapter(dataAdapter);
        jdst.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        List<String> srmanip = new ArrayList<String>();
        List<String> dsmanip = new ArrayList<String>();
        ArrayList<String> rsmanip = new ArrayList<String>();

        if (sk == dk)
            Toast.makeText(this, "Source and Destination must be different", Toast.LENGTH_LONG).show();
        else {
            switch (sk) {
                case 0:
                    srmanip = Guduvancherry;
                    break;
                case 1:
                    srmanip = Vandalur;
                    break;
                case 2:
                    srmanip = Tambaram;
                    break;
                case 3:
                    srmanip = Vadapalani;
                    break;
                case 4:
                    srmanip = Ambattur;
                    break;
                case 5:
                    srmanip = Avadi;
                    break;
                case 6:
                    srmanip = Perambur;
                    break;
                case 7:
                    srmanip = Egmore;
                    break;
                case 8:
                    srmanip = Madhavaram;
                    break;
                case 9:
                    srmanip = Adyar;
                    break;
                case 10:
                    srmanip = Porur;
                    break;
                case 11:
                    srmanip = Anna_Nagar;
                    break;
                case 12:
                    srmanip = Koyambedu;
                    break;
                case 13:
                    srmanip = Mangadu;
                    break;
                case 14:
                    srmanip = Thiruvotriyur;
                    break;
                case 15:
                    srmanip = Thiruverkadu;
                    break;
                default:
                    srmanip = Guduvancherry;
                    break;

            }
            switch (dk) {
                case 0:
                    dsmanip = Guduvancherry;
                    break;
                case 1:
                    dsmanip = Vandalur;
                    break;
                case 2:
                    dsmanip = Tambaram;
                    break;
                case 3:
                    dsmanip = Vadapalani;
                    break;
                case 4:
                    dsmanip = Ambattur;
                    break;
                case 5:
                    dsmanip = Avadi;
                    break;
                case 6:
                    dsmanip = Perambur;
                    break;
                case 7:
                    dsmanip = Egmore;
                    break;
                case 8:
                    dsmanip = Madhavaram;
                    break;
                case 9:
                    dsmanip = Adyar;
                    break;
                case 10:
                    dsmanip = Porur;
                    break;
                case 11:
                    dsmanip = Anna_Nagar;
                    break;
                case 12:
                    dsmanip = Koyambedu;
                    break;
                case 13:
                    dsmanip = Mangadu;
                    break;
                case 14:
                    dsmanip = Thiruvotriyur;
                    break;
                case 15:
                    dsmanip = Thiruverkadu;
                    break;
                default:
                    dsmanip = Tambaram;
                    break;

            }

            for (int i = 0; i < srmanip.size(); i++) {
                if (dsmanip.contains(srmanip.get(i))) {
                    rsmanip.add(srmanip.get(i));
                }
            }

            Intent intent = new Intent();
            intent.putExtra("List", rsmanip);
            intent.setClass(this, Busnum.class);
            startActivity(intent);
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.cspinsrc)
        {
            // srk = parent.getItemAtPosition(position).toString();
            // Toast.makeText(this,item,Toast.LENGTH_LONG).show();
            sk=position;
        }
        else if(spinner.getId() == R.id.cspindst)
        {
            // dsk = parent.getItemAtPosition(position).toString();
            dk=position;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
