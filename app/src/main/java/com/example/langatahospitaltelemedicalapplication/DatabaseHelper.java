package com.example.langatahospitaltelemedicalapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "telemedicine.db";
    private static final int DATABASE_VERSION = 1;

    // Doctor Table
    public static final String TABLE_DOCTORS = "doctors";
    public static final String COLUMN_DOCTOR_ID = "doctor_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_LICENSE = "license_number";

    public static final String COLUMN_SPECIALIZATION = "specialization";
    public static final String COLUMN_EMAIL = "email";

    public static final String COLUMN_PASSWORD = "password";

    // Patient Table
    public static final String TABLE_PATIENTS = "patients";
    public static final String COLUMN_PATIENT_ID = "patient_id";
    public static final String COLUMN_CONTACT = "contact_number";
    public static final String COLUMN_USERNAME = "username";

    // Appointment Table
    private static final String TABLE_APPOINTMENTS = "appointments";
    private static final String COLUMN_APPOINTMENT_ID = "appointment_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_NOTES = "notes";
    private static final String COLUMN_APPOINTMENT_STATUS = "appointment_status";

     //inquiry Table
    private static final String TABLE_INQUIRIES = "inquiries";
    private static final String COLUMN_INQUIRY_ID = "inquiry_id";
    private static final String COLUMN_MESSAGE = "message";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_PRIORITY = "priority";
    private static final String COLUMN_CREATED_AT = "created_at";
    private static final String COLUMN_INQUIRIES_STATUS = "inquiries_status";

    //Patient Records

    private static final String TABLE_PATIENT_RECORDS = "patient_records";
    private static final String COLUMN_RECORD_ID = "record_id";
    private static final String COLUMN_DIAGNOSIS = "diagnosis";
    private static final String COLUMN_TREATMENT = "treatment";
    private static final String COLUMN_MEDICATIONS = "medications";
    private static final String COLUMN_VISIT_DATE = "visit_date";
    private static final String COLUMN_DOCTOR_NOTES = "doctor_notes";
    private static final String COLUMN_BLOOD_PRESSURE = "blood_pressure";
    private static final String COLUMN_TEMPERATURE = "temperature";
    private static final String COLUMN_HEART_RATE = "heart_rate";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Doctor Table
        String CREATE_DOCTORS_TABLE = "CREATE TABLE " + TABLE_DOCTORS + "("
                + COLUMN_DOCTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FIRST_NAME + " TEXT, "
                + COLUMN_LAST_NAME + " TEXT, "
                + COLUMN_LICENSE + " TEXT UNIQUE, "
                + COLUMN_SPECIALIZATION + " TEXT, "
                + COLUMN_CONTACT + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PASSWORD + " TEXT)";

        db.execSQL(CREATE_DOCTORS_TABLE);

        // Patient Table
        String CREATE_PATIENTS_TABLE = "CREATE TABLE " + TABLE_PATIENTS + "("
                + COLUMN_PATIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FIRST_NAME + " TEXT, "
                + COLUMN_LAST_NAME + " TEXT, "
                + COLUMN_CONTACT + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_USERNAME + " TEXT UNIQUE, "
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_PATIENTS_TABLE);


        String CREATE_APPOINTMENTS_TABLE = "CREATE TABLE " + TABLE_APPOINTMENTS + "("
                + COLUMN_APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PATIENT_ID + " INTEGER, "
                + COLUMN_DOCTOR_ID + " INTEGER, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_NOTES + " TEXT, "
                + COLUMN_APPOINTMENT_STATUS + " TEXT DEFAULT 'pending', "
                + "FOREIGN KEY(" + COLUMN_PATIENT_ID + ") REFERENCES " + TABLE_PATIENTS + "(" + COLUMN_PATIENT_ID + "), "
                + "FOREIGN KEY(" + COLUMN_DOCTOR_ID + ") REFERENCES " + TABLE_DOCTORS + "(" + COLUMN_DOCTOR_ID + "))";
        db.execSQL(CREATE_APPOINTMENTS_TABLE);


        String CREATE_INQUIRIES_TABLE = "CREATE TABLE " + TABLE_INQUIRIES + "("
                + COLUMN_INQUIRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PATIENT_ID + " INTEGER, "
                + COLUMN_DOCTOR_ID + " INTEGER, "
                + COLUMN_MESSAGE + " TEXT, "
                + COLUMN_CATEGORY + " TEXT, "
                + COLUMN_PRIORITY + " TEXT, "
                + COLUMN_INQUIRIES_STATUS + " TEXT DEFAULT 'unread', "
                + COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY(" + COLUMN_PATIENT_ID + ") REFERENCES " + TABLE_PATIENTS + "(" + COLUMN_PATIENT_ID + "), "
                + "FOREIGN KEY(" + COLUMN_DOCTOR_ID + ") REFERENCES " + TABLE_DOCTORS + "(" + COLUMN_DOCTOR_ID + "))";
        db.execSQL(CREATE_INQUIRIES_TABLE);


        String CREATE_PATIENT_RECORDS_TABLE = "CREATE TABLE " + TABLE_PATIENT_RECORDS + "("
                + COLUMN_RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PATIENT_ID + " INTEGER, "
                + COLUMN_DOCTOR_ID + " INTEGER, "
                + COLUMN_DIAGNOSIS + " TEXT, "
                + COLUMN_TREATMENT + " TEXT, "
                + COLUMN_MEDICATIONS + " TEXT, "
                + COLUMN_VISIT_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + COLUMN_DOCTOR_NOTES + " TEXT, "
                + COLUMN_BLOOD_PRESSURE + " TEXT, "
                + COLUMN_TEMPERATURE + " TEXT, "
                + COLUMN_HEART_RATE + " TEXT, "
                + "FOREIGN KEY(" + COLUMN_PATIENT_ID + ") REFERENCES " + TABLE_PATIENTS + "(" + COLUMN_PATIENT_ID + "), "
                + "FOREIGN KEY(" + COLUMN_DOCTOR_ID + ") REFERENCES " + TABLE_DOCTORS + "(" + COLUMN_DOCTOR_ID + "))";
        db.execSQL(CREATE_PATIENT_RECORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);
        onCreate(db);
    }


    public boolean insertDoctor(String firstName, String lastName, String license,String Contact, String specialization, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_LICENSE, license);
        values.put(COLUMN_CONTACT, Contact);
        values.put(COLUMN_SPECIALIZATION, specialization);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_DOCTORS, null, values);
        return result != -1;
    }


    public int getDoctorId(String license, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DOCTORS,
                new String[]{COLUMN_DOCTOR_ID},
                COLUMN_LICENSE + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{license, password},
                null, null, null);

        if (cursor.moveToFirst()) {
            int doctorId = cursor.getInt(0);
            cursor.close();
            db.close();
            return doctorId;
        } else {
            return -1;
        }
    }
    public ArrayList<Doctor> FindDoctor() {
        ArrayList<Doctor> doctors = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DOCTORS,
                new String[]{COLUMN_DOCTOR_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME,
                        COLUMN_SPECIALIZATION, COLUMN_LICENSE, COLUMN_EMAIL},
                null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_ID));
                    String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME));
                    String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME));
                    String specialization = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SPECIALIZATION));


                    doctors.add(new Doctor(id, firstName,lastName,specialization));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return doctors;
    }


    public int getDoctorIdByName(String firstName, String lastName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DOCTORS,
                new String[]{COLUMN_DOCTOR_ID},
                COLUMN_FIRST_NAME + "=? AND " + COLUMN_LAST_NAME + "=?",
                new String[]{firstName, lastName},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int doctorId = cursor.getInt(0);
            cursor.close();
            return doctorId;
        }
        if (cursor != null) {
            cursor.close();
        }
        return -1;
    }


    public boolean insertPatient(String firstName, String lastName, String contact, String email, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_CONTACT, contact);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_PATIENTS, null, values);
        return result != -1;
    }

    public int getPatientId(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PATIENTS,
                new String[]{COLUMN_PATIENT_ID},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        if ( cursor != null && cursor.moveToFirst()) {
            int patientId = cursor.getInt(0);
            cursor.close();
            db.close();
            return patientId;
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return -1;
    }

    public boolean insertAppointment(int patientId, int doctorId, String date, String time, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PATIENT_ID, patientId);
        values.put(COLUMN_DOCTOR_ID, doctorId);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_NOTES, notes);

        long result = db.insert(TABLE_APPOINTMENTS, null, values);
        return result != -1;
    }

    public Cursor getAppointmentsForDoctor(int doctorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT a." + COLUMN_APPOINTMENT_ID + " as id, "
                + "a." + COLUMN_DATE + ", "
                + "a." + COLUMN_TIME + ", "
                + "a." + COLUMN_NOTES + ", "
                + "a." + COLUMN_APPOINTMENT_STATUS + ", "
                + "p." + COLUMN_FIRST_NAME + " || ' ' || p." + COLUMN_LAST_NAME + " AS patient_name "
                + "FROM " + TABLE_APPOINTMENTS + " a "
                + "JOIN " + TABLE_PATIENTS + " p ON a." + COLUMN_PATIENT_ID + " = p." + COLUMN_PATIENT_ID + " "
                + "WHERE a." + COLUMN_DOCTOR_ID + " = ?";

        return db.rawQuery(query, new String[]{String.valueOf(doctorId)});
    }

    public boolean updateAppointmentStatus(int appointmentId, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_APPOINTMENT_STATUS, status);
        int rows = db.update(TABLE_APPOINTMENTS, values,
                COLUMN_APPOINTMENT_ID + "=?",
                new String[]{String.valueOf(appointmentId)});
        db.close();
        return rows > 0;
    }

    public Cursor getAppointmentsForPatient(int patientId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT a." + COLUMN_APPOINTMENT_ID + " as id, "
                + "a." + COLUMN_DATE + ", "
                + "a." + COLUMN_TIME + ", "
                + "a." + COLUMN_NOTES + ", "
                + "a." + COLUMN_APPOINTMENT_STATUS + ", "
                + "d." + COLUMN_FIRST_NAME + " || ' ' || d." + COLUMN_LAST_NAME + " AS doctor_name "
                + "FROM " + TABLE_APPOINTMENTS + " a "
                + "JOIN " + TABLE_DOCTORS + " d ON a." + COLUMN_DOCTOR_ID + " = d." + COLUMN_DOCTOR_ID + " "
                + "WHERE a." + COLUMN_PATIENT_ID + "= ?";

        return db.rawQuery(query, new String[]{String.valueOf(patientId)});
    }

    public boolean insertInquiry(int patientId, int doctorId, String message, String category, String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PATIENT_ID, patientId);
        values.put(COLUMN_DOCTOR_ID, doctorId);
        values.put(COLUMN_MESSAGE, message);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_PRIORITY, priority);

        long result = db.insert(TABLE_INQUIRIES, null, values);
        return result != -1;
    }

    public Cursor getInquiriesForDoctor(int doctorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT i." + COLUMN_INQUIRY_ID + " as id, "
                + "i." + COLUMN_MESSAGE + ", "
                + "i." + COLUMN_CATEGORY + ", "
                + "i." + COLUMN_PRIORITY + ", "
                + "i." + COLUMN_INQUIRIES_STATUS + " as status, "
                + "i." + COLUMN_CREATED_AT + ", "
                + "p." + COLUMN_FIRST_NAME + " || ' ' || p." + COLUMN_LAST_NAME + " AS patient_name, "
                + "p." + COLUMN_CONTACT + " AS patient_contact "
                + "FROM " + TABLE_INQUIRIES + " i "
                + "JOIN " + TABLE_PATIENTS + " p ON i." + COLUMN_PATIENT_ID + " = p." + COLUMN_PATIENT_ID + " "
                + "WHERE i." + COLUMN_DOCTOR_ID + " = ? "
                + "ORDER BY i." + COLUMN_CREATED_AT + " DESC";

        return db.rawQuery(query, new String[]{String.valueOf(doctorId)});
    }

    public boolean updateInquiryStatus(int inquiryId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INQUIRIES_STATUS, status);

        int rows = db.update(TABLE_INQUIRIES, values,
                COLUMN_INQUIRY_ID + "=?",
                new String[]{String.valueOf(inquiryId)});
        return rows > 0;
    }


    public boolean insertPatientRecord(int patientId, int doctorId, String diagnosis,
                                       String treatment, String medications, String doctorNotes,
                                       String bloodPressure, String temperature, String heartRate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PATIENT_ID, patientId);
        values.put(COLUMN_DOCTOR_ID, doctorId);
        values.put(COLUMN_DIAGNOSIS, diagnosis);
        values.put(COLUMN_TREATMENT, treatment);
        values.put(COLUMN_MEDICATIONS, medications);
        values.put(COLUMN_DOCTOR_NOTES, doctorNotes);
        values.put(COLUMN_BLOOD_PRESSURE, bloodPressure);
        values.put(COLUMN_TEMPERATURE, temperature);
        values.put(COLUMN_HEART_RATE, heartRate);

        long result = db.insert(TABLE_PATIENT_RECORDS, null, values);
        return result != -1;
    }


    public Cursor getPatientRecordsForDoctor(int doctorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT r." + COLUMN_RECORD_ID + " as id, "
                + "r." + COLUMN_DIAGNOSIS + ", "
                + "r." + COLUMN_TREATMENT + ", "
                + "r." + COLUMN_MEDICATIONS + ", "
                + "r." + COLUMN_VISIT_DATE + ", "
                + "r." + COLUMN_DOCTOR_NOTES + ", "
                + "r." + COLUMN_BLOOD_PRESSURE + ", "
                + "r." + COLUMN_TEMPERATURE + ", "
                + "r." + COLUMN_HEART_RATE + ", "
                + "p." + COLUMN_FIRST_NAME + " || ' ' || p." + COLUMN_LAST_NAME + " AS patient_name "
                + "FROM " + TABLE_PATIENT_RECORDS + " r "
                + "JOIN " + TABLE_PATIENTS + " p ON r." + COLUMN_PATIENT_ID + " = p." + COLUMN_PATIENT_ID + " "
                + "WHERE r." + COLUMN_DOCTOR_ID + " = ? "
                + "ORDER BY r." + COLUMN_VISIT_DATE + " DESC";

        return db.rawQuery(query, new String[]{String.valueOf(doctorId)});
    }

    public Cursor getPatientsForDoctor(int doctorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT p." + COLUMN_PATIENT_ID + " as patient_id, "
                + "p." + COLUMN_FIRST_NAME + " || ' ' || p." + COLUMN_LAST_NAME + " AS patient_name "
                + "FROM " + TABLE_PATIENTS + " p "
                + "JOIN " + TABLE_APPOINTMENTS + " a ON p." + COLUMN_PATIENT_ID + " = a." + COLUMN_PATIENT_ID + " "
                + "WHERE a." + COLUMN_DOCTOR_ID + " = ? "
                + "ORDER BY patient_name";

        return db.rawQuery(query, new String[]{String.valueOf(doctorId)});
    }

    // Get patient details by ID
    public Cursor getPatientById(int patientId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_PATIENT_ID,
                COLUMN_USERNAME,
                COLUMN_FIRST_NAME,
                COLUMN_LAST_NAME,
                COLUMN_EMAIL,
                COLUMN_CONTACT
        };

        String selection = COLUMN_PATIENT_ID + " = ?";
        String[] selectionArgs = {String.valueOf(patientId)};

        return db.query(
                TABLE_PATIENTS,
                projection,
                selection,
                selectionArgs,
                null, null, null
        );
    }

    // Update patient profile
    public boolean updatePatientProfile(int patientId, String firstName, String lastName,
                                        String email, String contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_CONTACT, contact);

        String whereClause = COLUMN_PATIENT_ID + " = ?";
        String[] whereArgs = {String.valueOf(patientId)};

        int rowsAffected = db.update(TABLE_PATIENTS, values, whereClause, whereArgs);
        db.close();

        return rowsAffected > 0;
    }

    // Update patient username
    public boolean updatePatientUsername(int patientId, String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);

        String whereClause = COLUMN_PATIENT_ID + " = ?";
        String[] whereArgs = {String.valueOf(patientId)};

        int rowsAffected = db.update(TABLE_PATIENTS, values, whereClause, whereArgs);
        db.close();

        return rowsAffected > 0;
    }

    // Check if username already exists (for validation)
    public boolean doesUsernameExist(String username, int excludePatientId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM " + TABLE_PATIENTS +
                " WHERE " + COLUMN_USERNAME + " = ? AND " +
                COLUMN_PATIENT_ID + " != ?";

        Cursor cursor = db.rawQuery(query, new String[]{username, String.valueOf(excludePatientId)});

        boolean exists = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                exists = cursor.getInt(0) > 0;
            }
            cursor.close();
        }
        db.close();

        return exists;
    }

    // Check if email already exists (for validation)
    public boolean doesEmailExist(String email, int excludePatientId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM " + TABLE_PATIENTS +
                " WHERE " + COLUMN_EMAIL + " = ? AND " +
                COLUMN_PATIENT_ID + " != ?";

        Cursor cursor = db.rawQuery(query, new String[]{email, String.valueOf(excludePatientId)});

        boolean exists = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                exists = cursor.getInt(0) > 0;
            }
            cursor.close();
        }
        db.close();

        return exists;
    }


    // Get doctor by ID
    public Cursor getDoctorById(int doctorId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_DOCTOR_ID,
                COLUMN_LICENSE,
                COLUMN_FIRST_NAME,
                COLUMN_LAST_NAME,
                COLUMN_EMAIL,
                COLUMN_SPECIALIZATION,
                COLUMN_CONTACT,
        };

        String selection = COLUMN_DOCTOR_ID + " = ?";
        String[] selectionArgs = {String.valueOf(doctorId)};

        return db.query(
                TABLE_DOCTORS,
                projection,
                selection,
                selectionArgs,
                null, null, null
        );
    }

    // Update doctor profile
    public boolean updateDoctorProfile(int doctorId, String firstName, String lastName,
                                       String license, String email, String contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_LICENSE, license);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_CONTACT, contact);

        String whereClause = COLUMN_DOCTOR_ID + " = ?";
        String[] whereArgs = {String.valueOf(doctorId)};

        int rowsAffected = db.update(TABLE_DOCTORS, values, whereClause, whereArgs);
        db.close();

        return rowsAffected > 0;
    }

    // Check if license already exists
    public boolean doesLicenseExist(String license, int excludeDoctorId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM " + TABLE_DOCTORS +
                " WHERE " + COLUMN_LICENSE + " = ? AND " +
                COLUMN_DOCTOR_ID + " != ?";

        Cursor cursor = db.rawQuery(query, new String[]{license, String.valueOf(excludeDoctorId)});

        boolean exists = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                exists = cursor.getInt(0) > 0;
            }
            cursor.close();
        }
        db.close();

        return exists;
    }

    // Check if doctor email already exists
    public boolean doesDoctorEmailExist(String email, int excludeDoctorId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM " + TABLE_DOCTORS +
                " WHERE " + COLUMN_EMAIL + " = ? AND " +
                COLUMN_DOCTOR_ID + " != ?";

        Cursor cursor = db.rawQuery(query, new String[]{email, String.valueOf(excludeDoctorId)});

        boolean exists = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                exists = cursor.getInt(0) > 0;
            }
            cursor.close();
        }
        db.close();

        return exists;
    }



}
