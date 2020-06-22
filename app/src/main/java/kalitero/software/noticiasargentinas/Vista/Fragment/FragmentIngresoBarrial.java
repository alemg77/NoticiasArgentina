package kalitero.software.noticiasargentinas.Vista.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kalitero.software.noticiasargentinas.R;
import kalitero.software.noticiasargentinas.databinding.FragmentIngresoBarrialBinding;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;


public class FragmentIngresoBarrial extends Fragment {

    private FragmentIngresoBarrialBinding binding;


    public FragmentIngresoBarrial() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIngresoBarrialBinding.inflate(getLayoutInflater());

        binding.fragmentIngresoBarrialFABcategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // Set the alert dialog title
                builder.setTitle("Selecciene Categoría");


                final String[] temasNoticias = new String[]{
                        "Sociales",
                        "Policiales",
                        "Economía",
                        "Deportes",
                        "Artes"
                };

                builder.setSingleChoiceItems(
                        temasNoticias, // Items list
                        -1, // Index of checked item (-1 = no selection)
                        new DialogInterface.OnClickListener() // Item click listener
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Get the alert dialog selected item's text
                                String selectedItem = Arrays.asList(temasNoticias).get(i);

                                // Display the selected item's text on snack bar
                                binding.fragmentDetalleBarrialCategoriaTextView.setText(selectedItem);
                                Snackbar.make(
                                        getView(),
                                        "Seleccionó : " + selectedItem,
                                        Snackbar.LENGTH_LONG
                                ).show();
                            }
                        });

                // Set the a;ert dialog positive button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Just dismiss the alert dialog after selection
                        // Or do something now
                        dialogInterface.dismiss();
                    }
                });

                // Create the alert dialog
                AlertDialog dialog = builder.create();

                // Finally, display the alert dialog
                dialog.show();
            }
        });

        binding.fragmentIngresoBarrialFABsubirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EasyImage.openCameraForImage(FragmentIngresoBarrial.this,1);
                EasyImage.openChooserWithGallery(FragmentIngresoBarrial.this, "Seleccione la imagen de su noticia", 1);
            }
        });

        return binding.getRoot();

    }
        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
                @Override
                public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                    onPhotosReturned(imageFiles);
                }
            });
        }

        public void onPhotosReturned(List<File> imageFiles) {
            Bitmap imagen = BitmapFactory.decodeFile(imageFiles.get(0).getAbsolutePath());
            binding.fragmentIngresoBarrialImageViewFoto1.setImageBitmap(imagen);
        }
    }




