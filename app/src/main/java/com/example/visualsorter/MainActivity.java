package com.example.visualsorter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private ListView sortedLV, unsortedLV;
    private ArrayAdapter<String> sortedAA, unsortedAA;
    private int[] sortedNumbers, unsortedNumbers;
    private String[] sortedStrings, unsortedStrings;
    private int numberOfElements = 0;
    private int start = 0;
    private int end = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sortedLV = this.findViewById(R.id.sortedLV);
        this.unsortedLV = this.findViewById(R.id.unsortedLV);

        this.sortedNumbers = new int[this.numberOfElements];
        this.unsortedNumbers = new int[this.numberOfElements];
        this.sortedStrings = new String[this.numberOfElements];
        this.unsortedStrings = new String[this.numberOfElements];

        this.sortedAA = new ArrayAdapter<String>(this, R.layout.simple_listview_row, this.sortedStrings);
        this.unsortedAA = new ArrayAdapter<String>(this, R.layout.simple_listview_row, this.unsortedStrings);

        this.sortedLV.setAdapter(this.sortedAA);
        this.unsortedLV.setAdapter(this.unsortedAA);

        this.initializeArrays();
    }

    private void insertionSort(int[] ar)
    {
        int theFollower, swap;

        for(int currStart = 1; currStart < ar.length; currStart++)
        {
            theFollower = currStart;
            while(theFollower > 0 && ar[theFollower] < ar[theFollower-1])
            {
                swap = ar[theFollower];
                ar[theFollower] = ar[theFollower-1];
                ar[theFollower-1] = swap;
                theFollower--;
            }
        }
    }

    private void mergeSort(int[] ar)
    {
        if(ar.length == 1)
        {
            return;
        }

        int middle = ar.length/2;
        int[] left = new int[middle];
        int[] right = new int[ar.length - middle];

        for(int i = 0; i < middle; i++)
        {
            left[i] = ar[i];
        }
        for(int i = middle; i < ar.length; i++)
        {
            right[i - middle] = ar[i];
        }

        mergeSort(left);
        mergeSort(right);

        mergeSortHelper(ar, left, right, middle, ar.length - middle);

    }

    private void mergeSortHelper(int[] arrayToSort, int[] leftArray, int[] rightArray, int left, int right)
    {
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;

        while(count1 < left && count2 < right)
        {
            if(leftArray[count1] <= rightArray[count2])
            {
                arrayToSort[count3++] = leftArray[count1++];
            }
            else
            {
                arrayToSort[count3++] = rightArray[count2++];
            }
        }

        while(count1 < left)
        {
            arrayToSort[count3++] = leftArray[count1++];
        }
        while(count2 < right)
        {
            arrayToSort[count3++] = rightArray[count2++];
        }
    }

    public void insertionSortButtonPressed(View v)
    {
        this.insertionSort(this.unsortedNumbers);
        this.updateStringArrays();
    }

    public void mergeSortButtonPressed(View v)
    {
        this.mergeSort(this.unsortedNumbers);
        this.updateStringArrays();
    }

    public void generateButtonPressed(View v)
    {
        EditText countET = (EditText)findViewById(R.id.countET);
        this.numberOfElements = Integer.parseInt(countET.getText().toString());

        EditText startET = (EditText)findViewById(R.id.startET);
        this.start = Integer.parseInt(startET.getText().toString());

        EditText endET = (EditText)findViewById(R.id.endET);
        this.end = Integer.parseInt(endET.getText().toString());

        this.sortedNumbers = new int[this.numberOfElements];
        this.unsortedNumbers = new int[this.numberOfElements];
        this.sortedStrings = new String[this.numberOfElements];
        this.unsortedStrings = new String[this.numberOfElements];

        this.sortedAA = new ArrayAdapter<String>(this, R.layout.simple_listview_row, this.sortedStrings);
        this.unsortedAA = new ArrayAdapter<String>(this, R.layout.simple_listview_row, this.unsortedStrings);

        this.sortedLV.setAdapter(this.sortedAA);
        this.unsortedLV.setAdapter(this.unsortedAA);

        this.initializeArrays();
    }

    private void initializeArrays()
    {
        this.fillRandomIntArray(this.unsortedNumbers);
        this.copyContentsOfIntArrays(this.unsortedNumbers, this.sortedNumbers);
        this.updateStringArrays();
    }

    private void updateStringArrays()
    {
        this.copyIntArrayToStringArray(this.unsortedNumbers, this.unsortedStrings);
        this.copyIntArrayToStringArray(this.sortedNumbers, this.sortedStrings);
        this.sortedAA.notifyDataSetChanged();
        this.unsortedAA.notifyDataSetChanged();
    }

    private void copyIntArrayToStringArray(int[] arInts, String[] arStrings)
    {
        for(int i = 0; i < arInts.length; i++)
        {
            arStrings[i] = "" + arInts[i];

        }
    }

    private void copyContentsOfIntArrays(int[] source, int[] destination)
    {
        for(int i = 0; i < source.length; i++)
        {
            destination[i] = source[i];
        }
    }

    private void fillRandomIntArray(int[] ar)
    {
        Random r = new Random();


        for(int i = 0; i < ar.length; i++)
        {
            ar[i] = r.nextInt(this.end-this.start) + this.start;
        }
    }
}