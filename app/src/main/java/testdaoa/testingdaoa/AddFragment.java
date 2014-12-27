package testdaoa.testingdaoa;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.dao.manager.DBManager;



public class AddFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private View view;
    private DBManager dbManager;
    private EditText name;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.formadd,null);
        init();
        return view;
    }

    private void init() {
        dbManager = DBManager.getInstance(getActivity());
        name = (EditText)view.findViewById(R.id.editText);

        ((Button)view.findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("name",name.getText().toString());
                long id = dbManager.insert("tbl_name1",values);
                if (id > 0) {
                    Toast.makeText(getActivity(),"Added id "+id,Toast.LENGTH_LONG).show();
                    name.setText("");
                }
            }
        });
    }

}
