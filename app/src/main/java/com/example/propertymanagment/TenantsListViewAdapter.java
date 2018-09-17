package com.example.propertymanagment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.List;

public class TenantsListViewAdapter extends BaseAdapter {

    Activity activity;
    List<AddingTenants> tenants;
    LayoutInflater inflater;

    public TenantsListViewAdapter(Activity activity, List<AddingTenants> tenants) {
        this.activity = activity;
        this.tenants = tenants;
    }

    @Override
    public int getCount() {
        return tenants.size();
    }

    @Override
    public Object getItem(int i) {
        return tenants.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView  = inflater.inflate(R.layout.activity_all_tenants, null);

        EditText tenantName = (EditText) itemView.findViewById(R.id.);
        EditText tenantRent = (EditText) itemView.findViewById(R.id.);
        EditText tenantDeposit = (EditText)itemView.findViewById(R.id.);
        EditText tenant_rent_due_date = (EditText) itemView.findViewById(R.id.);

        tenantName.setText(tenants.get(i).getName());
        tenantRent.setText(tenants.get(i).getRent());
        tenantDeposit.setText(tenants.get(i).getDeposit());
        tenant_rent_due_date.setText(tenants.get(i).getRentDueDate());

        //repeat the above for deposit and due date

        return itemView;
    }
}
