package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ImageDecoderKt;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.ImageView;

import com.example.mobilodev.databinding.ActivityMainBinding;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    ArrayList books = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        if(books.size() == 0){
            books = setInfo();
        }
        else{
            DBRead();
        }




        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BookAdapter booksAdapter = new BookAdapter(books);
        binding.recyclerView.setAdapter(booksAdapter);

        DBWrite();
    }


    private ArrayList<Book> setInfo() {
        Book book1 = new Book("Nutuk", "Mustafa Kemal Atatürk", R.drawable.nutuk, "Nutuk, yazıldığı dönemde Cumhuriyet Halk Fırkası umumi reisi olan Gazi Mustafa Kemal'in 15-20 Ekim 1927 tarihlerinde, 1919'dan 1927'ye dek kendisinin ve silah arkadaşlarının faaliyetlerini özetlediği konuşmasının metnidir.");

        Book book2 = new Book("Beyaz Zambaklar Ülkesinde", "Grigoriy Petrov", R.drawable.beyaz, "Yazar Grigory Petrov'un çeşitli aralıklarla çıktığı Finlandiya seyahatlerindeki notlardan oluşan eser 1800'lerin son döneminde Finlandiya halkının içinde bulunduğu durumu, cehaletten kurtulmak için başta Johan Vilhelm Snellman olmak üzere ülkedeki bir avuç Fin aydının verdiği olağanüstü mücadeleyi anlatır.");

        Book book3 = new Book("Bir Borsa Spekülatörünün Anıları", "Edvin Lefevre", R.drawable.borsa, "Borsanın kuralları, bu kitabın ilk yayınlandığı 1923'ten bu yana önemli ölçüde değişmiş olsa da insan doğası neredeyse hiç değişmedi. Korku, açgözlülük, umut ve gurur bugün 1900'lerin başındakiyle aynı ve bu temel temel insani duygular, küçük yatırımcılardan büyük hedge fonu yöneticilerine kadar çeşitli insanların yükselişini ve düşüşünü açıklayabilir.\n" +
                "Bu kitap, yalnızca borsadan kazandıklarıyla dünyanın en zengin insanları arasına giren efsanevi Wall Street tüccarı Jesse Livermore'un hayatını, sayısız başarısızlıklarını, çelişkilerini ve bunlardan ders çıkararak kendiyle mücadelesini ve elbette başarılarını anlatır. Doğru mali tabloların nadiren yayınlandığı, mevcut hisse senedi fiyatlarının alınmasının büyük bir operasyon gerektirdiği ve piyasa manipülasyonunun yaygın olduğu bir zamanda Livermore, alım satımlarının temeli olarak şimdi teknik analiz olarak bilinen şeyi kullandı. Livermore gelmiş geçmiş en büyük borsacılardan biri olarak kabul edilir. Piyasalarda bugün için kullanılan kalıplaşmış çoğu ifadenin de kaynağı bu kitaptır.\n" +
                "\n");

        Book book4 = new Book("1984", "George Orwell", R.drawable.bin, "1947-1948 yıllarında yazılan roman 1984 yıllarındaki hayali bir dünyayı konu alır. 1984 yılını anlatan romanda bu yıllar hiçte iç açıcı anlatılmaz. Özgürlüğün olmadığı, yaşam kalitesinin diplerde olduğu ve buna rağmen bu durumların eskisinden çok daha iyi olduğuna inandırıldığı bir dünya mevcuttur.");

        Book book5 = new Book("Hayvan Çiftliği", "George Orwell", R.drawable.hayvan, "Hayvan Çiftliği, (orijinal adıyla Animal Farm) George Orwell'in mecazi bir dille yazılmış, fabl tarzındaki siyasi hiciv romanı. Roman ilk olarak 1945'te Birleşik Krallık'ta yayımlandı. 1996'da ise geçmiş tarihler için verilen Retro Hugo Ödülü'nü 1946 senesi için aldı. Roman, Stalinizmin eleştirisidir.");

        Book book6 = new Book("Dönüşüm", "Franz Kafka", R.drawable.donusum, "Eserin konusu: Gregor Samsa'nın dönüşümüdür. Öyküde; yalnızlık, dışlanmışlık ve yabancılaşma temaları işlenmektedir. Eser üzerinden “kapitalist düzene ayak uydurmayıp sistemin istediği gibi olmayan insanlar, yakın çevresindekiler ve toplum tarafından dışlanıp otoritesini kaybeder” tezi aktarılmaktadır.");

        Book book7 = new Book("Aklından Bir Sayı Tut", "John Verdon", R.drawable.sayi, "Aklında Bir Sayı Tut, bir seri katil ile onun peşine düşen bir dedektifin heyecan dolu kovalamacasını konu ediniyor. Bu katilin kurban seçtiği kişilerin ortak bir noktası var. Peki ama ne? Bu romanı okurken merakınıza engel olamayacak ve olayların sonunu asla tahmin edemeyeceksiniz!");


        Book book8 = new Book("Küçük Prens", "Antoine de Saint-Exupéry", R.drawable.prens, "Küçük Prens, kendi galaksisinde, kendi dünyasında tek bir gül ile yaşayan bir Küçük Prens'in başka galaksileri gezmek için tek gülünü tek başına bırakıp yolculuk yapmasını anlatır. Ancak bu yolculuk esnasında vurgulamak istediği şey 'büyümek'tir.");

        Book book9 = new Book("Sineklerin Tanrısı", "William Golding", R.drawable.sinek, "Jack ve grubunun Ralph'ı öldürmek için tekrar aradıkları sırada adaya bir gemi yanaşır ve çocukları kurtarır. Sineklerin Tanrısı, Liderlik savaşının insanların doğal yapısında olduğunu ve bunu kazanmak için de dost kazanma ve düşman kaybetme (gerekirse yok etme) yöntemlerini uygulamasını gösteren bir roman.");

        Book book10 = new Book("Kürk Mantolu Madonna\n", "Sabahattin Ali", R.drawable.madonna, "Hüzünlü bir aşk hikayesi olan Kürk Mantolu Madonna, iki hikayeden oluşan bir anlatıma haizdir. İlk hikayede Rasim adlı karakterin iş bulması ile Raif Efendi karakteriyle tanışması anlatılır. İkinci hikayede ise Raif Efendi'nin kimseye söyleyemediği anlatamadığı aşk hikayesi göze çarpar.");

        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);
        books.add(book7);
        books.add(book8);
        books.add(book9);
        books.add(book10);

        return books;
    }


    private void DBWrite(){


        SQLiteDatabase database = this.openOrCreateDatabase("Books", Context.MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS Book(id INTEGER PRIMARY KEY,bookName VARCHAR, authorName VARCHAR, bookImage INTEGER, sumText VARCHAR)");
        try {

            for (int i =0; i<books.size();i++){
                Book b = (Book) books.get(i);
                String AutName = b.Author;
                String BookName = b.Bookname;
                String Sumtext = b.TextSum;
                int ImageUri = b.Image;

                /*
                Bitmap image = makeSmallerImage(Bitmap.createBitmap(b.Image,100,Bitmap.Config.ARGB_8888));
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG,50,outputStream);
                byte[] byteImage = outputStream.toByteArray();
                */


                String sqlString = "INSERT INTO Book (bookname, authorName, bookImage, sumText) VALUES (?,?,?,?)";
                SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
                sqLiteStatement.bindString(1,BookName);
                sqLiteStatement.bindString(2,AutName);
                sqLiteStatement.bindString(3,Integer.toString(ImageUri));
                sqLiteStatement.bindString(4,Sumtext);
                sqLiteStatement.execute();
            }
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            database.close();
        }
    }

    private void DBRead(){
        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Books",MODE_PRIVATE,null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Book",null);
        try {
            int authorIx = cursor.getColumnIndex("authorName");
            int bookNameIx = cursor.getColumnIndex("bookName");
            int bookImageIx = cursor.getColumnIndex("bookImage");
            int sumTextIx = cursor.getColumnIndex("sumText");

            Book book;
            while(cursor.moveToNext()){

                String author = cursor.getString(authorIx);
                String bookName = cursor.getString(bookNameIx);
                int ImageUri = (Integer) cursor.getInt(bookImageIx);
                String sumText = cursor.getString(sumTextIx);

                book = new Book(author,bookName,ImageUri,sumText);
                books.add(book);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            cursor.close();
            sqLiteDatabase.close();
        }
    }

    public Bitmap makeSmallerImage(Bitmap image){
        return image.createScaledBitmap(image,75,100,true);
    }
}