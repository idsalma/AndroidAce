package com.example.projectacesalma;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectacesalma.models.TypeCompte;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText soldeInput;
    private Spinner typeSpinner, formatSpinner;
    private LinearLayout compteContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des widgets
        soldeInput = findViewById(R.id.soldeInput);
        typeSpinner = findViewById(R.id.typeSpinner);
        formatSpinner = findViewById(R.id.formatSpinner);
        Button createButton = findViewById(R.id.createButton);
        compteContainer = findViewById(R.id.compteContainer);

        // Initialiser les Spinners
        initializeTypeSpinner();
        initializeFormatSpinner();

        // Gestion du bouton "Add"
        createButton.setOnClickListener(v -> {
            String soldeText = soldeInput.getText().toString();
            String selectedType = typeSpinner.getSelectedItem().toString();

            if (soldeText.isEmpty()) {
                Toast.makeText(this, "Please enter solde", Toast.LENGTH_SHORT).show();
            } else {
                double solde = Double.parseDouble(soldeText);
                addCompteView(selectedType, solde);
                soldeInput.setText(""); // Réinitialiser le champ après ajout
            }
        });

        // Gestion de la sélection du format (JSON ou XML)
        formatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String format = parent.getItemAtPosition(position).toString();
                handleFormatSelection(format);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // Initialisation des types de comptes
    private void initializeTypeSpinner() {
        ArrayList<String> types = new ArrayList<>();
        for (TypeCompte type : TypeCompte.values()) {
            types.add(type.name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    // Initialisation des formats (JSON/XML)
    private void initializeFormatSpinner() {
        ArrayList<String> formats = new ArrayList<>();
        formats.add("JSON");
        formats.add("XML");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, formats);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        formatSpinner.setAdapter(adapter);
    }

    // Gérer la sélection du format (JSON ou XML)
    private void handleFormatSelection(String format) {
        // Simuler une logique réseau pour JSON ou XML
        if (format.equals("JSON")) {
            Toast.makeText(this, "JSON format selected", Toast.LENGTH_SHORT).show();
            simulateNetworkRequest("JSON");
        } else if (format.equals("XML")) {
            Toast.makeText(this, "XML format selected", Toast.LENGTH_SHORT).show();
            simulateNetworkRequest("XML");
        }
    }

    // Simuler une requête réseau (remplacez par une vraie logique si nécessaire)
    private void simulateNetworkRequest(String format) {
        // Simule l'envoi ou la réception de données en JSON/XML
        Toast.makeText(this, "Simulating " + format + " network request...", Toast.LENGTH_SHORT).show();
    }

    // Ajouter un compte à la vue
    private void addCompteView(String type, double solde) {
        LinearLayout compteLayout = new LinearLayout(this);
        compteLayout.setOrientation(LinearLayout.HORIZONTAL);
        compteLayout.setPadding(8, 8, 8, 8);
        compteLayout.setBackgroundColor(Color.parseColor("#EFEFEF"));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 8, 0, 8);
        compteLayout.setLayoutParams(layoutParams);

        // Affichage des détails du compte
        TextView compteInfo = new TextView(this);
        compteInfo.setText(type + ": " + solde);
        LinearLayout.LayoutParams infoParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        compteInfo.setLayoutParams(infoParams);

        // Bouton Edit
        Button editButton = new Button(this);
        editButton.setText("Edit");
        editButton.setOnClickListener(v -> {
            soldeInput.setText(String.valueOf(solde));
            typeSpinner.setSelection(((ArrayAdapter<String>) typeSpinner.getAdapter()).getPosition(type));
            compteContainer.removeView(compteLayout);
        });

        // Bouton Delete
        Button deleteButton = new Button(this);
        deleteButton.setText("Delete");
        deleteButton.setOnClickListener(v -> {
            compteContainer.removeView(compteLayout);
            Toast.makeText(this, "Compte deleted", Toast.LENGTH_SHORT).show();
        });

        // Ajouter les widgets au layout du compte
        compteLayout.addView(compteInfo);
        compteLayout.addView(editButton);
        compteLayout.addView(deleteButton);

        // Ajouter le layout au conteneur principal
        compteContainer.addView(compteLayout);
    }
}
