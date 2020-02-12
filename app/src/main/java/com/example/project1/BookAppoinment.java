package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.Calendar;

public class BookAppoinment extends AppCompatActivity
{
    EditText date;
    Spinner time;
    Button nextbtn;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appoinment);

        date=(EditText)findViewById(R.id.getDate);
        time=(Spinner)findViewById(R.id.getTime);
        nextbtn=(Button)findViewById(R.id.BookAppoinmentNext);

       Calendar calendar = Calendar.getInstance();
        final int year=calendar.get(calendar.YEAR);
        final int month=calendar.get(calendar.MONTH);
        final int day=calendar.get(calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*DatePickerDialog datePickerDialog=new DatePickerDialog(
                  getApplicationContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();*/

                DatePickerDialog datePickerDialog=new DatePickerDialog(BookAppoinment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        month=month+1;
                        String date1=dayOfMonth+"/"+month+"/"+year;
                        date.setText(date1);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }
}
