package com.vctapps.starwarscharacters.persistence.files;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Class ManagerJsonFiles que gerencia onde os arquivos JSON que devem ser salvos.
 */

public class ManagerJsonFiles {
    private static final String TAG = "ManagerJson";

    /**
     * Salva JSON na memória interna do dispositivo.
     * @param context : @Link Context
     * @param json : String com conteúdo do json
     * @param fileName : String com nome do arquivo
     */
    public static boolean save(Context context, String json, String fileName){
        try{
            OutputStreamWriter out =
                    new OutputStreamWriter(context.
                            openFileOutput(fileName + ".txt", Context.MODE_PRIVATE));
            out.write(json);
            out.close();
            return true;
        }catch (IOException e){
            Log.e(TAG, "Não foi possível salvar o arquivo: " + fileName + ".txt");
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    /**
     * Recupera JSONs salvos na memória interna do dispositivo
     * @param context : @Link Context
     * @param fileName : Nome do arquivo
     * @return String : String com conteúdo do JSON. Caso aconteça algum erro para recuperar
     * a String retorna vazia
     */
    public static String get(Context context, String fileName){
        String json = "";

        try {
            InputStream inputStream = context.openFileInput(fileName + ".txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                json = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e(TAG, "Não foi possível encontrar o arquivo: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Não foi possível ler o arquivo: " + e.toString());
        }

        return json;
    }

    /**
     * Deleta JSONs salvos anteriormente
     * @param context : @Link Context
     * @param fileName : Nome do arquivo
     * @return boolean : true se conseguiu deletar, false caso contrário
     */
    public static boolean delete(Context context, String fileName){
        return context.deleteFile(fileName + ".txt");
    }

    /**
     * Verifica se existe um arquivo JSON com o nome solicitado
     * @param context : Context
     * @param fileName : Nome do arquivo
     * @return boolean : true se o arquivo existe, false caso contrário
     */
    public static boolean hasJsonStorage(Context context, String fileName){
        try {
            context.openFileInput(fileName + ".txt");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
