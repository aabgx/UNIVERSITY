package com.example.tema_bun.protobuff;

import com.example.tema_bun.Utilizator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProtoUtils {
    public static AppProtobuffs.RequestP createLoginRequest(Utilizator utilizator){
        AppProtobuffs.UtilizatorP utilizatorP=AppProtobuffs.UtilizatorP.newBuilder().setUsername(utilizator.getUsername()).setParola(utilizator.getParola()).build();
        AppProtobuffs.RequestP request= AppProtobuffs.RequestP.newBuilder().setType(AppProtobuffs.RequestP.Type.LOGIN)
                .setUtilizatorP(utilizatorP).build();
        return request;
    }

    public static AppProtobuffs.RequestP createGetNumberOfParticipantsRequest()
    {
        AppProtobuffs.RequestP request=AppProtobuffs.RequestP.newBuilder().setType(AppProtobuffs.RequestP.Type.GET_NUMBER_OF_PARTICIPANTS).build();
        return request;
    }


    public static AppProtobuffs.RequestP createGetProbaCategorieParticipantsRequest(String proba, String categorie)
    {
        AppProtobuffs.RequestP request=AppProtobuffs.RequestP.newBuilder().setType(AppProtobuffs.RequestP.Type.GET_PROBA_CATEGORIE_PARTICIPANTS).setStr(proba+" "+categorie).build();
        return request;
    }


    //BA NUJ SINCER DACA ASTA E OK
/*    public static AppProtobuffs.RequestP createInscriereRequest(String nume, Integer varsta, String probe,String usernameCurent) throws Exception
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String[] inscriereData = new String[]{nume, varsta.toString(), objectMapper.writeValueAsString(probe),usernameCurent};

        AppProtobuffs.RequestP request=AppProtobuffs.RequestP.newBuilder()
                .setType(AppProtobuffs.RequestP.Type.INSCRIERE)
                .addAllInscriereData(Arrays.asList(inscriereData))
                .build();
        return request;
    }*/
    public static AppProtobuffs.RequestP createInscriereRequest(String[] inscriereData) throws Exception
    {
        AppProtobuffs.RequestP request=AppProtobuffs.RequestP.newBuilder()
                .setType(AppProtobuffs.RequestP.Type.INSCRIERE)
                .addAllInscriereData(Arrays.asList(inscriereData))
                .build();
        return request;
    }


    public static AppProtobuffs.RequestP createLogoutRequest(Utilizator utilizator){
        AppProtobuffs.UtilizatorP utilizatorP=AppProtobuffs.UtilizatorP.newBuilder().setUsername(utilizator.getUsername()).setParola(utilizator.getParola()).build();
        AppProtobuffs.RequestP request= AppProtobuffs.RequestP.newBuilder().setType(AppProtobuffs.RequestP.Type.LOGOUT)
                .setUtilizatorP(utilizatorP).build();
        return request;
    }


    //RESPONSE
    public static List<Integer> getVectorCntFromResponse(AppProtobuffs.ResponseP response)
    {
        List<Integer> vector_cnt=new ArrayList<>();
        response.getVectorCntList().forEach(el->{
            vector_cnt.add(el);});
        return vector_cnt;
    }

    //NICI ASTA NUJ SINCER DACA E BN
    public static List<String> getSearchedFromResponse(AppProtobuffs.ResponseP response)
    {
        List<String> lista_participanti=new ArrayList<>();
        response.getProbaCategorieList().forEach(el->{
            lista_participanti.add(el);
        });
        return lista_participanti;
    }
}
