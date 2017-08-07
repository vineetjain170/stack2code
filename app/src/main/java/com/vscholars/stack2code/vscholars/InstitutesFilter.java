package com.vscholars.stack2code.vscholars;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vineet_jain on 22/3/17.
 */

public class InstitutesFilter extends AppCompatActivity{
    ListView parentList,childFilterOptions;
    int iconsForParentOptions[]={R.drawable.ic_year,R.drawable.ic_state,R.drawable.ic_program,R.drawable.ic_level,R.drawable.ic_institution,R.drawable.ic_minority,R.drawable.ic_women};
    Button filter;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.institutes_filter);
        parentList=(ListView)findViewById(R.id.parent_options);
        childFilterOptions=(ListView)findViewById(R.id.child_options);

        ActionBar ac = getSupportActionBar();
        ac.hide();

        final List<String> optionsCategory=new ArrayList<String>();
        final List<String> yearOptions=new ArrayList<String>();
        List<String> stateOptions=new ArrayList<String>();
        List<String> programOptions=new ArrayList<String>();
        List<String> levelOptions=new ArrayList<String>();
        List<String> institutionType=new ArrayList<String>();
        List<String> minority=new ArrayList<String>();
        List<String> women=new ArrayList<String>();
        final HashMap<String,List<String>> childList=new HashMap<String, List<String>>();
        final HashMap<String,Integer>selectedOptions=new HashMap<String, Integer>();

        final String optionsCategoryS[]=getResources().getStringArray(R.array.filterOptionsMain);
        final String yearOptionS[]=getResources().getStringArray(R.array.yearOptions);
        final String stateOptionsS[]=getResources().getStringArray(R.array.stateOptions);
        final String programOptionsS[]=getResources().getStringArray(R.array.programOptions);
        final String levelOptionsS[]=getResources().getStringArray(R.array.levelOptions);
        final String instituteTypeS[]=getResources().getStringArray(R.array.institutionType);
        final String minorityS[]=getResources().getStringArray(R.array.minorityOptions);
        final String womenS[]=getResources().getStringArray(R.array.womenOptions);

        for (String opt:optionsCategoryS)optionsCategory.add(opt);
        for (String year:yearOptionS)yearOptions.add(year);
        for (String state:stateOptionsS)stateOptions.add(state);
        for (String program:programOptionsS)programOptions.add(program);
        for (String level:levelOptionsS)levelOptions.add(level);
        for (String institute:instituteTypeS)institutionType.add(institute);
        for (String min:minorityS)minority.add(min);
        for (String wom:womenS)women.add(wom);

        childList.put(optionsCategory.get(0),yearOptions);
        childList.put(optionsCategory.get(1),stateOptions);
        childList.put(optionsCategory.get(2),programOptions);
        childList.put(optionsCategory.get(3),levelOptions);
        childList.put(optionsCategory.get(4),institutionType);
        childList.put(optionsCategory.get(5),minority);
        childList.put(optionsCategory.get(6),women);

        selectedOptions.put("year",-1);
        selectedOptions.put("state",-1);
        selectedOptions.put("program",-1);
        selectedOptions.put("level",-1);
        selectedOptions.put("institute",-1);
        selectedOptions.put("minority",-1);
        selectedOptions.put("women",-1);
        filter=(Button)findViewById(R.id.action_php_submit);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(InstitutesFilter.this,InstitutesListMain.class);
                i.putExtra("userType",getIntent().getIntExtra("userType",0));
                startActivity(i);
            }
        });

        parentList.setAdapter(new AdapterForParentList(this,optionsCategoryS,-1,0xFFFFFFFF,iconsForParentOptions));
        parentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                parentList.setAdapter(new AdapterForParentList(InstitutesFilter.this,optionsCategoryS,position,0xFF00FF00,iconsForParentOptions));
                view.setSelected(true);

                if (position==0){
                    if (selectedOptions.get("year")!=-1){

                        childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,yearOptionS, selectedOptions.get("year"),0xFF00FF00));
                    }
                    else if (selectedOptions.get("year")==-1){
                        childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,yearOptionS, -1,0xFF00FF00));
                    }
                    childFilterOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,yearOptionS, selectedOptions.get("year"),0xFFFFFFFF));
                            selectedOptions.put("year",position);
                            childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,yearOptionS, selectedOptions.get("year"),0xFF00FF00));
                        }
                    });

                }else if (position==1){
                    if (selectedOptions.get("state")!=-1){
                        childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,stateOptionsS, selectedOptions.get("state"),0xFF00FF00));
                    }
                    else if (selectedOptions.get("state")==-1){
                        childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,stateOptionsS, -1,0xFF00FF00));
                    }
                    childFilterOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,stateOptionsS, selectedOptions.get("state"),0xFFFFFFFF));
                            selectedOptions.put("state",position);
                            childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,stateOptionsS, selectedOptions.get("state"),0xFF00FF00));
                        }
                    });
                }else if (position==2){
                    if (selectedOptions.get("program")!=-1){
                        childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,programOptionsS, selectedOptions.get("program"),0xFF00FF00));
                    }
                    else if (selectedOptions.get("program")==-1){
                        childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,programOptionsS, -1,0xFF00FF00));
                    }
                    childFilterOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,programOptionsS, selectedOptions.get("program"),0xFFFFFFFF));
                            selectedOptions.put("program",position);
                            childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,programOptionsS, selectedOptions.get("program"),0xFF00FF00));
                        }
                    });
                }else if (position==3){
                    if (selectedOptions.get("level")!=-1){
                        childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,levelOptionsS, selectedOptions.get("level"),0xFF00FF00));
                    }
                    else if (selectedOptions.get("level")==-1){
                        childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,levelOptionsS, -1,0xFF00FF00));
                    }
                    childFilterOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,levelOptionsS, selectedOptions.get("level"),0xFFFFFFFF));
                            selectedOptions.put("level",position);
                            childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,levelOptionsS, selectedOptions.get("level"),0xFF00FF00));
                        }
                    });
                }else if (position==4){
                    if (selectedOptions.get("institute")!=-1){
                        childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,instituteTypeS, selectedOptions.get("institute"),0xFF00FF00));
                    }
                    else if (selectedOptions.get("institute")==-1){
                        childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,instituteTypeS, -1,0xFF00FF00));
                    }
                    childFilterOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,instituteTypeS, selectedOptions.get("institute"),0xFFFFFFFF));
                            selectedOptions.put("institute",position);
                            childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,instituteTypeS, selectedOptions.get("institute"),0xFF00FF00));
                        }
                    });
                }else if (position==5){
                    if (selectedOptions.get("minority")!=-1){
                        childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,minorityS, selectedOptions.get("minority"),0xFF00FF00));
                    }
                    else if (selectedOptions.get("minority")==-1){
                        childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,minorityS, -1,0xFF00FF00));
                    }
                    childFilterOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,minorityS, selectedOptions.get("minority"),0xFFFFFFFF));
                            selectedOptions.put("minority",position);
                            childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,minorityS, selectedOptions.get("minority"),0xFF00FF00));
                        }
                    });
                }else if (position==6){
                    if (selectedOptions.get("women")!=-1){
                        childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,womenS, selectedOptions.get("women"),0xFF00FF00));
                    }
                    else if (selectedOptions.get("women")==-1){
                        childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,womenS, -1,0xFF00FF00));
                    }
                    childFilterOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,womenS, selectedOptions.get("women"),0xFFFFFFFF));
                            selectedOptions.put("women",position);
                            childFilterOptions.setAdapter(new AdapterForChildList(InstitutesFilter.this,womenS, selectedOptions.get("women"),0xFF00FF00));
                        }
                    });
                }
            }
        });
    }
    public void onBackPressed(){
        Intent intent=new Intent(InstitutesFilter.this,InstitutesListMain.class);
        intent.putExtra("userType",getIntent().getIntExtra("userType",0));
        startActivity(intent);
    }
}
