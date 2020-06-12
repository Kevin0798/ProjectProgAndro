package com.example.projectprogandro;



public class InfoPenumpang {

             private String Tujuan;
             private String Asal;
             private String Harga;
             private String Dewasa;
             private String Tanggal;
             private String Anak;

             public InfoPenumpang(){
                 //Empty
             }


             public InfoPenumpang(String Tujuan, String Harga, String Tanggal, String Asal, String Dewasa, String Anak){
                 this.Tujuan = Tujuan;
                 this.Harga = Harga;
                 this.Tanggal = Tanggal;
                 this.Asal = Asal;
                 this.Dewasa = Dewasa;
                 this.Anak = Anak;
             }

                         public String getTujuan(){

                             return Tujuan;
                         }


                         public String getHarga(){

                             return Harga;
                         }


                         public String getTanggal(){
                             return Tanggal;
                         }



                         public String getAsal(){

                             return Asal;
                         }



                         public String getDewasa(){

                             return Dewasa;
                         }


                         public String getAnak(){

                             return Anak;
                         }



            }
