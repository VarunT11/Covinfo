package com.example.covinfo.api;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covinfo.R;
import com.example.covinfo.classes.DistrictStats;
import com.example.covinfo.classes.IndiaStats;
import com.example.covinfo.classes.News;
import com.example.covinfo.classes.StateStats;
import com.example.covinfo.enums.TimeSeriesType;
import com.example.covinfo.interfaces.ApiManagerInterface;
import com.example.covinfo.interfaces.FetchApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityApiManager {

    private AppCompatActivity activity;
    private ApiManagerInterface apiManagerInterface;

    private ApiSingleton apiSingleton;

    private final String root_url;

    public ActivityApiManager(AppCompatActivity activity, ApiManagerInterface apiManagerInterface) {
        this.activity = activity;
        this.apiManagerInterface = apiManagerInterface;

        root_url = activity.getString(R.string.backend_url);
        apiSingleton = ApiSingleton.getInstance(activity);
    }

    public void getNewsHeadlinesIndia() {
        String url = root_url + "india/news/";

        apiSingleton.sendGetRequest(url, (success, result) -> {
            if (success) {
                ArrayList<News> newsList = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        newsList.add(new News(jsonArray.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                apiManagerInterface.onNewsFetchComplete(true, newsList, null);
            } else {
                apiManagerInterface.onNewsFetchComplete(false, null, result);
            }
        });
    }

    public void getOverallStatsIndia() {
        String url = root_url + "india/";

        apiSingleton.sendGetRequest(url, (success, result) -> {
            if (success) {
                IndiaStats indiaStats = new IndiaStats();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    indiaStats = new IndiaStats(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                apiManagerInterface.onIndiaStatsFetch(true, indiaStats, null);
            } else {
                apiManagerInterface.onIndiaStatsFetch(false, null, result);
            }
        });
    }

    public void getTimeSeriesDataIndia(TimeSeriesType timeSeriesType) {
        String type = "";
        if (timeSeriesType == TimeSeriesType.WEEK) {
            type = "last_week/";
        } else if (timeSeriesType == TimeSeriesType.MONTH) {
            type = "last_month/";
        }
        String url = root_url + "india/timeseries/" + type;

        apiSingleton.sendGetRequest(url, (success, result) -> {
            if (success) {
                ArrayList<IndiaStats> indiaTimeSeriesData = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        indiaTimeSeriesData.add(new IndiaStats(jsonArray.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                apiManagerInterface.onIndiaTimeSeriesStatsFetch(true, indiaTimeSeriesData, null);
            } else {
                apiManagerInterface.onIndiaTimeSeriesStatsFetch(false, null, result);
            }
        });
    }

    public void getStatesDataList() {
        String data_url = root_url + "state/";

        apiSingleton.sendGetRequest(data_url, (success, result) -> {
            if (success) {
                ArrayList<StateStats> statsArrayList = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        statsArrayList.add(new StateStats(jsonObject));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                apiManagerInterface.onStatesDataListFetch(true, statsArrayList, null);
            } else {
                apiManagerInterface.onStatesDataListFetch(false, null, result);
            }
        });
    }

    public void getStateData(String stateCode) {
        String data_url = root_url + "state/" + stateCode;

        apiSingleton.sendGetRequest(data_url, (success, result) -> {
            if (success) {
                StateStats stateStats = new StateStats();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    stateStats = new StateStats(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                apiManagerInterface.onStateDataFetch(true, stateStats, null);
            } else {
                apiManagerInterface.onStateDataFetch(false, null, result);
            }
        });
    }

    public void getStateTimeSeriesData(TimeSeriesType timeSeriesType, String stateCode) {
        String type = "";
        if (timeSeriesType == TimeSeriesType.WEEK) {
            type = "last_week/";
        } else if (timeSeriesType == TimeSeriesType.MONTH) {
            type = "last_month/";
        }
        String info_url = root_url + "state/info/" + stateCode;
        String data_url = root_url + "state/" + stateCode + "/timeseries/" + type;

        apiSingleton.sendGetRequest(info_url, (success, result) -> {
            if (success) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String code = jsonObject.getString("code");
                    String name = jsonObject.getString("name");

                    apiSingleton.sendGetRequest(data_url, (success1, result1) -> {
                        if (success1) {
                            ArrayList<StateStats> stateTimeSeriesData = new ArrayList<>();
                            try {
                                JSONArray jsonArray = new JSONArray(result1);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    stateTimeSeriesData.add(new StateStats(code, name, jsonArray.getJSONObject(i)));
                                }
                                apiManagerInterface.onStateTimeSeriesDataFetch(true, stateTimeSeriesData, null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                apiManagerInterface.onStateTimeSeriesDataFetch(false, null, e.getLocalizedMessage());
                            }
                        } else {
                            apiManagerInterface.onStateTimeSeriesDataFetch(false, null, result1);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    apiManagerInterface.onStateTimeSeriesDataFetch(false, null, e.getLocalizedMessage());
                }
            } else {
                apiManagerInterface.onStateTimeSeriesDataFetch(false, null, result);
            }
        });
    }

    public void getDistrictsDataList(String stateCode) {
        String info_url = root_url + "state/info/" + stateCode;
        String data_url = root_url + "state/" + stateCode + "/districts/";

        apiSingleton.sendGetRequest(info_url, (success, result) -> {
            if (success) {
                try {
                    JSONObject stateInfoObject = new JSONObject(result);
                    String stateName = stateInfoObject.getString("name");
                    apiSingleton.sendGetRequest(data_url, (success1, result1) -> {
                        if (success1) {
                            try {
                                ArrayList<DistrictStats> districtDataList = new ArrayList<>();
                                JSONArray jsonArray = new JSONArray(result1);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    districtDataList.add(new DistrictStats(stateCode, stateName, jsonArray.getJSONObject(i)));
                                }
                                apiManagerInterface.onDistrictsListFetch(true, districtDataList, null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                apiManagerInterface.onDistrictsListFetch(false, null, e.getLocalizedMessage());
                            }
                        } else {
                            apiManagerInterface.onDistrictsListFetch(false, null, result1);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    apiManagerInterface.onDistrictsListFetch(false, null, e.getLocalizedMessage());
                }
            } else {
                apiManagerInterface.onDistrictsListFetch(false, null, result);
            }
        });
    }

    public void getDistrictData(String stateCode, String districtName) {
        String info_url = root_url + "state/info/" + stateCode;
        String data_url = root_url + "state/" + stateCode + "/districts/" + districtName;

        apiSingleton.sendGetRequest(info_url, (success, result) -> {
            if (success) {
                try {
                    JSONObject stateInfoObject = new JSONObject(result);
                    String stateName = stateInfoObject.getString("name");
                    apiSingleton.sendGetRequest(data_url, (success1, result1) -> {
                        if (success1) {
                            try {
                                JSONObject jsonObject = new JSONObject(result1);
                                DistrictStats districtStat = new DistrictStats(stateCode, stateName, jsonObject);
                                apiManagerInterface.onDistrictDataFetch(true, districtStat, null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                apiManagerInterface.onDistrictDataFetch(false, null, e.getLocalizedMessage());
                            }
                        } else {
                            apiManagerInterface.onDistrictDataFetch(false, null, result1);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    apiManagerInterface.onDistrictDataFetch(false, null, e.getLocalizedMessage());
                }
            } else {
                apiManagerInterface.onDistrictDataFetch(false, null, result);
            }
        });
    }

    public void getDistrictTimeSeriesData(TimeSeriesType timeSeriesType, String stateCode, String districtName) {
        String type = "";
        if (timeSeriesType == TimeSeriesType.WEEK) {
            type = "last_week/";
        } else if (timeSeriesType == TimeSeriesType.MONTH) {
            type = "last_month/";
        }
        String info_url = root_url + "state/info/" + stateCode;
        String data_url = root_url + "state/" + stateCode + "/districts/" + districtName + "/timeseries/" + type;

        apiSingleton.sendGetRequest(info_url, (success, result) -> {
            if (success) {
                try {
                    JSONObject stateInfoObject = new JSONObject(result);
                    String stateName = stateInfoObject.getString("name");
                    apiSingleton.sendGetRequest(data_url, (success1, result1) -> {
                        if (success1) {
                            try {
                                ArrayList<DistrictStats> districtDataList = new ArrayList<>();
                                JSONArray jsonArray = new JSONArray(result1);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    districtDataList.add(new DistrictStats(stateCode, stateName, districtName, jsonArray.getJSONObject(i)));
                                }
                                apiManagerInterface.onDistrictTimeSeriesDataFetch(true, districtDataList, null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                apiManagerInterface.onDistrictTimeSeriesDataFetch(false, null, e.getLocalizedMessage());
                            }
                        } else {
                            apiManagerInterface.onDistrictTimeSeriesDataFetch(false, null, result1);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    apiManagerInterface.onDistrictTimeSeriesDataFetch(false, null, e.getLocalizedMessage());
                }
            } else {
                apiManagerInterface.onDistrictTimeSeriesDataFetch(false, null, result);
            }
        });
    }

}
