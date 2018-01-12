package com.example.alex.habit.controller;

import android.content.Context;

import com.example.alex.habit.model.HabitDateEntity;
import com.example.alex.habit.model.HabitEntity;
import com.example.alex.habit.model.HabitRepository;
import com.example.alex.habit.model.UserEntity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HabitController implements Subject {
    private static final String HOST = "10.0.2.2";
    private static final Integer PORT = 5000;
    private static final String URL = "http://" + HOST + ":" + PORT;
    private static final AsyncHttpClient httpClient = new AsyncHttpClient();

    private HabitRepository repository;
    private List<Observer> observers;

    private static HabitController instance = null;

    private HabitController(Context context) {
        repository = HabitRepository.getInstance(context);
        observers = new ArrayList<>();
    }

    public static HabitController getInstance(Context context) {
        if (instance == null) {
            instance = new HabitController(context);
        }
        return instance;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(Observer.ObserverStatus status, Object object) {
        for (Observer observer : observers) {
            observer.update(status, object);
        }
    }

    private void fetchHabits(final String email) {
        httpClient.get(URL + "/habits", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray object = new JSONArray(new String(responseBody));
                    List<HabitEntity> habits = new ArrayList<>();
                    for (int i = 0; i < object.length(); ++i) {
                        HabitEntity entity = new HabitEntity(object.getJSONObject(i));
                        if (entity.getEmail().equals(email)) {
                            habits.add(entity);
                        }
                    }
                    repository.resetHabits(habits, email);
                    notifyObservers(Observer.ObserverStatus.OK, repository.getHabitList(email));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                notifyObservers(Observer.ObserverStatus.FAIL, "Can't connect to server");
            }
        });
    }

    public List<HabitEntity> getHabitList(String email) {
        fetchHabits(email);
        return repository.getHabitList(email);
    }

    public void addHabit(final HabitEntity habit) {
        try {
            JSONObject object = habit.toJSON();
            RequestParams params = new RequestParams();
            params.put("habit", object);
            httpClient.post(URL + "/habits", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    fetchHabits(habit.getEmail());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    notifyObservers(Observer.ObserverStatus.FAIL, "Can't connect to server");
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteHabit(final HabitEntity habit) {
        try {
            JSONObject object = habit.toJSON();
            RequestParams params = new RequestParams();
            params.put("habit", object);
            httpClient.delete(URL + "/habits", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    fetchHabits(habit.getEmail());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    notifyObservers(Observer.ObserverStatus.FAIL, "Can't connect to server");
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fetchUsers() {
        httpClient.get(URL + "/users", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray object = new JSONArray(new String(responseBody));
                    List<UserEntity> users = new ArrayList<>();
                    for (int i = 0; i < object.length(); ++i) {
                        UserEntity entity = new UserEntity(object.getJSONObject(i));
                        users.add(entity);
                    }
                    repository.resetUsers(users);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                notifyObservers(Observer.ObserverStatus.FAIL, "Can't connect to server");
            }
        });
    }

    public List<UserEntity> getUserList() {
        fetchUsers();
        return repository.getUserList();
    }

    public UserEntity getUser(String email) {
        List<UserEntity> users = getUserList();
        for (UserEntity user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(UserEntity user) {
        try {
            JSONObject object = user.toJSON();
            RequestParams params = new RequestParams();
            params.put("user", object);
            httpClient.post(URL + "/users", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    fetchUsers();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    notifyObservers(Observer.ObserverStatus.FAIL, "Can't connect to server");
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fetchDates() {
        httpClient.get(URL + "/dates", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray object = new JSONArray(new String(responseBody));
                    List<HabitDateEntity> habitDateEntities = new ArrayList<>();
                    for (int i = 0; i < object.length(); ++i) {
                        HabitDateEntity entity = new HabitDateEntity(object.getJSONObject(i));
                        habitDateEntities.add(entity);
                    }
                    repository.resetHabitDates(habitDateEntities);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                notifyObservers(Observer.ObserverStatus.FAIL, "Can't connect to server");
            }
        });
    }

    public List<Date> getDates(int habitId) {
        fetchDates();
        List<Date> dates = new ArrayList<>();
        List<HabitDateEntity> habitDateEntities = repository.getHabitDateList();
        for (HabitDateEntity habitDateEntity : habitDateEntities) {
            if (habitDateEntity.getHabitId() == habitId) {
                dates.add(habitDateEntity.getDate());
            }
        }
        return dates;
    }

    public void addHabitDate(final HabitDateEntity habitDateEntity) {
        try {
            JSONObject object = habitDateEntity.toJSON();
            RequestParams params = new RequestParams();
            params.put("habit_date", object);
            httpClient.post(URL + "/dates", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    fetchDates();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    notifyObservers(Observer.ObserverStatus.FAIL, "Can't connect to server");
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deleteHabitDate(HabitDateEntity habitDateEntity) {
        try {
            JSONObject object = habitDateEntity.toJSON();
            RequestParams params = new RequestParams();
            params.put("habit_date", object);
            httpClient.delete(URL + "/dates", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    fetchDates();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    notifyObservers(Observer.ObserverStatus.FAIL, "Can't connect to server");
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
