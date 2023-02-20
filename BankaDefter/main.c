#include <stdio.h>
#include <string.h>
#include <stdlib.h>

struct Hesap{
	int hesapno;
	char ad[10];
	char soyad[20];
	char telefon[10];
	int bakiye;
} hesaplar[100];
int hesapKontrol(int hesapno){
	if(hesaplar[hesapno].hesapno==-1){
		return 0;
	}
	else return 1;
}
void hesapekle(){
	char ad[10];
	char soyad[20];
	char telefon[10];
	int hesapno;
	int bakiye;
	printf("Hesap no giriniz:");
	scanf("%d",&hesapno);
	if(hesapKontrol(hesapno)==0){
		printf("Hesap Sahibinin;\n Adi:");
		scanf("%s",ad);
		printf("Soyadi:");
		scanf("%s",soyad);
		printf("Telefon:");
		scanf("%s",telefon);
		printf("Bakiye:");
		scanf(" %d",&bakiye);
		hesaplar[hesapno].bakiye=bakiye;
		hesaplar[hesapno].hesapno=hesapno;
		strcpy(hesaplar[hesapno].ad,ad);
		strcpy(hesaplar[hesapno].soyad,soyad);
		strcpy(hesaplar[hesapno].telefon,telefon);
		printf("\n-------------------\nMusteri Kaydi Eklendi\n-------------------\n");
	}
	else{
		printf("\n***************\nBU HESAP ZATEN KAYITLI\n***************\n");
	}
}
void hesapSil(int id){
	if(hesapKontrol(id)==1){
		hesaplar[id].hesapno=-1;
		printf("\n***************\nHESAP SILINDI\n***************\n");
	}
	else{	
	printf("\n***************\nBU NUMARAYA AIT BIR HESAP BULUNAMADI\n***************\n");	
	}
}
void hesapGuncelle(){
	int hesapno,bakiye;
	printf("Guncellenecek hesap numarasini giriniz:");
	scanf(" %d",&hesapno);
	if(hesapKontrol(hesapno)==1){
	printf("Bakiyeyi giriniz:");
	scanf(" %d",&bakiye);
	hesaplar[hesapno].bakiye=bakiye;
	printf("Hesap Bakiyesi Guncellendi..\n");
	printf("Yeni Bakiye: %d\n",hesaplar[hesapno].bakiye);
	}
	else{
	printf("\n***************\nBU NUMARAYA AIT BIR HESAP BULUNAMADI\n***************\n");	
	}
}
void borclumusteriler(){
	int i;
	FILE* kaydet = fopen("BorcluHesaplar.txt","w");
			for(i=0;i<100;i++){
				if(hesapKontrol(i)==1){
					if(hesaplar[i].bakiye<0){
					fprintf(kaydet,"Hesap No: %d\n",hesaplar[i].hesapno);
					fprintf(kaydet,"Musteri Bilgileri: \nAd Soyad: %s %s\n",hesaplar[i].ad,hesaplar[i].soyad);
					fprintf(kaydet,"Telefon: %s\n",hesaplar[i].telefon);
					fprintf(kaydet,"Bakiye: %dTL\n",hesaplar[i].bakiye);
					}
				}
			} 
	fclose(kaydet);	
}
void bakiyesiolanmusteriler(){
	int i;
	FILE* kaydet = fopen("BakiyesiOlanHesaplar.txt","w");
			for(i=0;i<100;i++){
				if(hesapKontrol(i)==1){
					if(hesaplar[i].bakiye>=0){
					fprintf(kaydet,"Hesap No: %d\n",hesaplar[i].hesapno);
					fprintf(kaydet,"Musteri Bilgileri: \nAd Soyad: %s %s\n",hesaplar[i].ad,hesaplar[i].soyad);
					fprintf(kaydet,"Telefon: %s\n",hesaplar[i].telefon);
					fprintf(kaydet,"Bakiye: %dTL\n",hesaplar[i].bakiye);
					}
				}
			} 
	fclose(kaydet);
}
void Kaydet(){
	int i;
	FILE* kaydet = fopen("Hesapbilgileri.dat","w");
			for(i=0;i<100;i++){
				if(hesapKontrol(i)==1){
					fprintf(kaydet,"Hesap No: %d\n",hesaplar[i].hesapno);
					fprintf(kaydet,"Musteri Bilgileri: \nAd Soyad: %s %s\n",hesaplar[i].ad,hesaplar[i].soyad);
					fprintf(kaydet,"Telefon: %s\n",hesaplar[i].telefon);
					fprintf(kaydet,"Kalan Bakiye: %dTL\n",hesaplar[i].bakiye);
				}
			} 
	fclose(kaydet);
}

int main() {
	int i;
	for(i=0;i<100;i++){
		hesaplar[i].hesapno=-1;
	}
	int secenek=-1;
	while(secenek!=0){
		printf("\n*****************************************\n\n");
		printf("0-Cikis\n");
		printf("1-Musteri Listesi\n");
		printf("2-Musteri Kaydi Ac\n");
		printf("3-Musteri Kaydi Sil\n");
		printf("4-Bakiye Guncelle\n");
		printf("5-Borclu Musterileri Disa Aktar\n");
		printf("6-Bakiyesi Olan Musterileri Disa Aktar\n");
		printf("7-Hesap Bilgilerini Kaydet\n");
		printf("\n*****************************************\n");
		scanf(" %d",&secenek);
		switch(secenek){
			case 0: break;
			case 1:	
			printf("Musteri Listesi:\n");
			for(i=0;i<100;i++){
				if(hesapKontrol(i)==1){
					printf("Hesap No: %d\n",hesaplar[i].hesapno);
					printf("Musteri Bilgileri: \nAd Soyad: %s %s\n",hesaplar[i].ad,hesaplar[i].soyad);
					printf("Telefon: %s\n",hesaplar[i].telefon);
					printf("Kalan Bakiye: %dTL\n",hesaplar[i].bakiye);
				}
			}
			break;
			case 2:
				hesapekle();
				break;
			case 3:
				printf("Silmek istediginiz hesap numarasini giriniz:");
				int hesap;
				scanf(" %d",&hesap);
				hesapSil(hesap);
				break;
			case 4:
				hesapGuncelle();
				break;
			case 5:
				borclumusteriler();
				printf("Borclu musterilerin listesi kaydedildi.\n");
				break;
			case 6:
				bakiyesiolanmusteriler();
				printf("Bakiyesi olan musterilerin listesi kaydedildi.\n");
				break;
			case 7:
				Kaydet();
				printf("Musteril bilgileri Hesapbilgileri.dat dosyasina kaydedildi.\n");
				break;
			default:
				printf("Gecersiz secim.\n");
				break;
						
		}
	}
	return 0;
}
