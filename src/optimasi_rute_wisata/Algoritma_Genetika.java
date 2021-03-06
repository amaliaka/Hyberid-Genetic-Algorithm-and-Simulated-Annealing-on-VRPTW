package optimasi_rute_wisata;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 *
 * @author amaliaka
 */
public class Algoritma_Genetika {

    private double[][] time_wisata; // titik satu dengan dua [wisata i][wisata j]
    private double[][] time_hotel;  // titik hotel dan wisata [hotel i][hotel j]
    private double[][] time_windows; // waktu buka pariwisata
    private int[][] chromosom;
    private int[][] offspring;
    private double[][] fitness_chromosom;
    private double[][] fitness_offspring;
    //private final boolean replacement_selection = true; // tanda untuk seleksi replacement
    private int no_hotel;
    private double cr = 0.3, mr = 0.1;// parameter algoritma genetika

    // method yang menjalankan proses algoritma genetika
    public Algoritma_Genetika(double[][] time_wisata, double[][] time_hotel, double[][] time_windows, int no_hotel) {
        int iterasi = 800; //generasi
        this.no_hotel = no_hotel;
        int n = 400; // banyak chromosom / populasi
        this.time_wisata = time_wisata;
        this.time_hotel = time_hotel;
        this.time_windows = time_windows;
        main(iterasi, n, no_hotel);
//        best_fitness(fitness_chromosom);

    }

    // method untuk menjalankan program
    public void main(int iterasi, int n, int no_hotel) {
////        double start = System.nanoTime();
//        inisialisasi(n, no_hotel);
//        int t = 0;
////        double total_time = 0;
////        int pp = pilih(2);
////        SA(pp);
////        while (total_time < 60.0) {
//        while (t < iterasi) {
//            replace_reproduksi();
//            evaluasi();
//            run_SA();
//            System.out.println(t + 1 + " " + best_fitness(fitness_chromosom));
////            best_fitness(fitness_chromosom);
////            best_solution(fitness_chromosom);
////            double finish = System.nanoTime();
////            total_time = (finish-start)* 1.0e-9;
//            t++;
//        }
////        System.out.println("Fitness Akhir = "+best_fitness(fitness_chromosom));
////        System.out.println(best_fitness(fitness_chromosom));
//        best_solution(fitness_chromosom);
        test();
    }

    // method untuk memilih kromosom
    public int pilih(int kondisi) {
        int t = 0;
        if (kondisi == 0) {//fitness terbaik
            double besar = fitness_chromosom[0][fitness_chromosom[0].length - 1];
            for (int i = 1; i < fitness_chromosom.length; i++) {
                if (fitness_chromosom[i][fitness_chromosom[0].length - 1] > besar) {
                    besar = fitness_chromosom[i][fitness_chromosom[0].length - 1];
                    t = i;
                }
            }
        } else if (kondisi == 1) {//fitness terburuk
            double kecil = fitness_chromosom[0][fitness_chromosom[0].length - 1];
            for (int i = 1; i < fitness_chromosom.length; i++) {
                if (fitness_chromosom[i][fitness_chromosom[0].length - 1] < kecil) {
                    kecil = fitness_chromosom[i][fitness_chromosom[0].length - 1];
                    t = i;
                }
            }
        } else if (kondisi == 2) {//random
            double random = Math.random() * (chromosom.length);
            Double tt = new Double(random);
            t = tt.intValue();
        }
        return t;
    }

    public void run_SA() {
        int pp = pilih(0);
        SA(pp);
        int pp2 = pilih(1);
        SA(pp2);
    }

    public void test() {
        int[] S = {4, 18, 9, 12, 13, 3, 6, 11, 8, 16, 15, 5, 10, 1, 17, 2, 14, 7, 0};
        double fitnesss = fitness1(S, true);
        System.out.println("Fitness S = " + fitnesss);
    }

    // method untuk SA
    public void SA(int p) {
//        double start = System.nanoTime();
        // chromosome
//        int[] S = {3, 8, 13, 4, 18, 9, 10, 16, 5, 6, 11, 12, 17, 15, 1, 2, 14, 7, 0, 6}; // solusi awal
        int[] S = chromosom[p];
        int[] Sn = new int[chromosom[0].length];
        // temperature
        double temp0 = 0.9;
        double temp1 = 0.01;
        double t = temp0;
        double cooling_factor = 0.9;
        double total_time = 0;
        // fitness
//        System.out.println("Kromosom Awal");
//        System.out.println(Arrays.toString(S));
//        System.out.println("Fitness awal = " + fitness(S));
//        System.out.println("================================================");
//        NGELU NDASE
        double d2 = 0; // fitness dari Sn
        // proses SA
//        while (total_time < 60.0) {
        while (t >= temp1) {
            double d1 = fitness(S); // fitness dari S
//            System.out.println("Fitness s = "+d1);
            Sn = pembangkitan_chromosome_SA(S, p);
            // hitung d2
            d2 = fitness(Sn);
//            System.out.println("Suhu = " + t);
//            System.out.println("d1 = " + d1 + " d2 = " + d2);
            // kondisi
            if (d2 > d1) {
                S = Sn;
//                System.out.println(Arrays.toString(S));
//                System.out.println("================================================");
            } else {
                int k = 200;
//                double prob = 1 / (Math.exp(((d1 - d2) * k) / (d1 * t)));
                double prob = 1 / (Math.exp(((d1 - d2) * k) / (t)));
//                System.out.println("Probabilitas = " + prob);
                // kondisi d2
                double alpa = Math.random();
//                System.out.println("Alpha = " + alpa);
                if (alpa < prob) {
                    S = Sn;
                    //System.out.println(Arrays.toString(S));
                    //System.out.println("Fitness Sn = " + fitness(S));
                }
//                System.out.println(Arrays.toString(S));
//                System.out.println("================================================");
            }
//            System.uot.println(Arrays.toString(S));
//            System.out.println("Fitness Sn = " + fitness(S));
//            System.out.println("================================================");
//            if  (t > 0.01) {
//                t = t * cooling_factor;
//            }
//            else {
//                t = 0.01;
//            }
//            System.out.println(fitness(S));
//            double finish = System.nanoTime();
//            total_time = (finish-start)* 1.0e-9;
            t = t * cooling_factor;
        }
        chromosom[p] = S; // add new solution in GA's from SA
//        System.out.println(fitness(S));
//        fitness(S, true);
//        System.out.println(Arrays.toString(S));
//        System.out.println("-----------");
    }

    // method untuk membangkitkan nilai Sn
    public int[] pembangkitan_chromosome_SA(int[] S, int p) {
        int[] chromosome = new int[S.length];
        int kondisi = 1; // variabel untuk pemilihan operator SA: 1 = scramble mutation
        //int kk = 0;
        //while (kondisi(p) && kk == 0) {
        if (kondisi == 0) {
            chromosome = random_mutation();
        } else if (kondisi == 1) {
            chromosome = scramble_mutation(S);
        }
        //kk = 1;
        //}
        return chromosome;
    }

    // membangkitkan nilai gen tiap chromosom sebanyak n
    public int[] random_mutation() {
        int[] hasil = new int[20];
        int ii = 0, x = 0;
        // membangkitkan segmen pertama secara random
        while (ii < 19) {
            // batasan nilai [1 19]
            double random = Math.random() * (20 - 0) + 0;
            Double r = new Double(random);
            int tanda = 0;
            for (int j = 0; j < hasil.length; j++) {
                if (r.intValue() == hasil[j]) {
                    tanda++;
                }
            }
            if (tanda == 0) {
                hasil[ii++] = r.intValue();
            }
            tanda = 0;
        }
        // membangkitkan segmen kedua secara random
        // inisialisasi nilai no. hotel
        hasil[hasil.length - 1] = no_hotel;
        // mengurangi dengan 1
        for (int j = 0; j < hasil.length; j++) {
            if (hasil[j] == 0) {

            } else {
                hasil[j] -= 1;
            }
        }
        return hasil;
    }

    // method untuk melakukan scramble mutation
    public int[] scramble_mutation(int[] S) {
        int[] new_S = new int[S.length];
        // bangkitkan dua titik q dan r
        int pp = 0;
        int q = 0; // batas depan
        int r = 0; // batas belakang
        while (pp != 1) {
            Double qq = new Double(Math.random() * (S.length - 1));
            q = qq.intValue();
            Double rr = new Double(Math.random() * (S.length - 1));
            r = rr.intValue();
            if (r > q) {
                pp = 1;
            }
        }
//        System.out.println("Batas Awal = " + q);
//        System.out.println("Batas Akhir = " + r);
        int[] kondisi = new int[1 + r - q];// array baru dengan batas q dan r
        for (int i = 0; i < kondisi.length; i++) {
            kondisi[i] = 999;//inisialisasi biasa
        }
        int s = 0;
        for (int i = 0; i < new_S.length; i++) {
            if (i >= q && i <= r) {
                // random
                int boll = 0;
                int kk = 0;
                while (boll != 1) {
                    Double k = Math.random() * (1 + r - q) + q; //random index gen dalam range
                    kk = k.intValue();
                    for (int j = 0; j < kondisi.length; j++) {
                        if (kondisi[j] == kk) { // melihat angka yang sama dlm kromosom
                            break;
                        }
                        if (j == kondisi.length - 1) {
                            boll = 1;//kondisi selesai
                            kondisi[s++] = kk;
                        }
                    }
                }
                int nilai = S[kk];
                new_S[i] = nilai;
            } else {
                new_S[i] = S[i];
            }
        }
        return new_S;
    }

    // nilai fitness terbaik 
    public double best_fitness(double[][] fitness) {
        double hasil = fitness[0][fitness[0].length - 1];
        int kk = 0;
        for (int i = 0; i < fitness.length; i++) {
            if (fitness[i][fitness[0].length - 1] > hasil) {
                hasil = fitness[i][fitness[0].length - 1];
                kk = i;
            }
        }
        return hasil;
    }

    // kromosom terbaik 
    public double best_solution(double[][] fitness) {
        double hasil = fitness[0][fitness[0].length - 1];
        int kk = 0;
        for (int i = 0; i < fitness.length; i++) {
            if (fitness[i][fitness[0].length - 1] > hasil) {
                hasil = fitness[i][fitness[0].length - 1];
                kk = i;
            }
        }
        fitness(chromosom[kk], true);
        for (int i = 0; i < chromosom[kk].length - 1; i++) {
            System.out.print(" " + chromosom[kk][i]);
        }
        System.out.println();
        return hasil;
    }

    // inisialisasi chromosom
    public void inisialisasi(int n, int no_hotel) {
        // create objek from chromosom
        Chromosom c = new Chromosom();
        c.create(n);
        chromosom = c.inisialisasi(no_hotel);
        fitness_chromosom = new double[chromosom.length][chromosom[0].length + 1];//inisialisasi sarray untuk nilai fitness
        double child_crossover = cr * chromosom.length;
        Double cc = new Double(child_crossover);
        double child_mutation = mr * chromosom.length;
        Double cm = new Double(child_mutation);//jadikan object
        // inisiaslisasi offspring
        offspring = new int[cc.intValue() + cm.intValue()][20];
        fitness_offspring = new double[offspring.length][offspring[0].length + 1];
    }

    // melakukan reproduksi offspring [replacement selection]
    public void replace_reproduksi() {
        double child_crossover = cr * chromosom.length;
        Double cc = new Double(child_crossover);
        double child_mutation = mr * chromosom.length;
        Double cm = new Double(child_mutation);
        // inisiaslisasi offspring
        offspring = new int[cc.intValue() + cm.intValue()][20];
        replace_crossover(cc.intValue());
        replace_mutation(cc.intValue(), offspring.length);
    }

    // crossover menggunakan one cut poin [replacement selection]
    public void replace_crossover(int t) {
        // random 2 individu, batasan 0 - n
        for (int x = 0; x < t; x++) {
            int r = 0, random1 = 0, random2 = 0;
            while (r != 1) {
                random1 = (int) (Math.random() * (chromosom.length - 1));//random index parent 1
                random2 = (int) (Math.random() * (chromosom.length - 1));//random index parent 2
                if (random1 != random2) {
                    r = 1;
                }
            }
            // random satu titik dalam gen chromosom
            int randomT = (int) (Math.random() * (chromosom[0].length - 1));
            // nilai dikiri titik
            int k = 0;
            for (int i = 0; i <= randomT; i++) {
                offspring[x][k++] = chromosom[random1][i];
            }
            // nilai dikanan titik
            for (int l = 0; l < chromosom[random2].length - 1; l++) {
                if (k == chromosom[0].length) {
                    break;
                }
                int hasil = 0;
                for (int j = 0; j < offspring[x].length; j++) {
                    if (chromosom[random2][l] == offspring[x][j]) {
                        hasil = 1;
                        break;
                    }
                }
                // apakah kosong, jika ya masukan
                if (hasil == 0) {
                    offspring[x][k] = chromosom[random2][l];
                    if (l != chromosom[0].length - 1) {
                        k++;
                    }
                } else {
                    hasil = 0;
                }
            }
            offspring[x][offspring[0].length - 1] = chromosom[x][chromosom[0].length - 1];
            // melakukan replacement selection
            // melihat nilai fitness offspring[x], chromosom[random1], chromosom[random2]
            double fitness_parent1 = replace_evaluasi(chromosom[random1]);
            double fitness_parent2 = replace_evaluasi(chromosom[random2]);
            double fitness_offspring = replace_evaluasi(offspring[x]);
            double fitness_parent_kecil = 0;
            int tanda = 0;
            //mencari fitness patrent terkecil
            if (fitness_parent1 < fitness_parent2) {
                fitness_parent_kecil = fitness_parent1;
                tanda = random1;
            } else {
                fitness_parent_kecil = fitness_parent2;
                tanda = random2;
            }
            //membandingkan fitness parent dengan offspring
            if (fitness_offspring >= fitness_parent_kecil) {
                for (int i = 0; i < chromosom[0].length; i++) {
                    chromosom[tanda][i] = offspring[x][i];
                }
            }
        }
    }

    // mutation menggunakan reciprocal exchange [replacement selection]
    public void replace_mutation(int t, int tt) {
        for (int i = t; i < tt; i++) {
            int r = 0, random1 = 0, random2 = 0, random_kromosom = 0;
            // memilih 2 titik gen secara acak
            while (r == 0) {
                random1 = (int) (Math.random() * (chromosom[i].length - 1));//titik 1
                random2 = (int) (Math.random() * (chromosom[i].length - 1));//titik 2
                if (random1 != random2) {
                    r = 1;
                }
            }
            // memilih 1 chromosom secara acak
            random_kromosom = (int) (Math.random() * (chromosom.length));
            for (int j = 0; j < chromosom[random_kromosom].length; j++) {
                offspring[i][j] = chromosom[random_kromosom][j];
            }
            //menukar nilai gen kedua titik
            int sementara = offspring[i][random1];
            offspring[i][random1] = offspring[i][random2];
            offspring[i][random2] = sementara;
            // melakukan replacement selection
            double fitness_offspring = replace_evaluasi(offspring[i]);
            double fitness_parent = replace_evaluasi(chromosom[random_kromosom]);
            if (fitness_offspring > fitness_parent) {
                chromosom[random_kromosom] = offspring[i];
            }
        }
    }

    // menghitung nilai fitness tiap chromsom [replacement selection]
    public double replace_evaluasi(int[] chromosom) {
        double fitness = 0;
        fitness = replace_fitness(chromosom);
        return fitness;
    }

    // menghitung nilai fitness [replacement selection]
    public double replace_fitness(int[] nilai) {
        return (fitness(nilai));
    }

    // menghitung nilai fitness tiap chromsom
    public void evaluasi() {
        //inisialisasi variabel fitness_kromosom dengan nilai gen kromosom
        for (int i = 0; i < fitness_chromosom.length; i++) {
            for (int j = 0; j < fitness_chromosom[i].length - 1; j++) {
                fitness_chromosom[i][j] = chromosom[i][j];
            }
        }
        //inisialisasi variabel fitness_offspring dengan nilai gen kromosom offspring hasil reproduksi
        for (int i = 0; i < fitness_offspring.length; i++) {
            for (int j = 0; j < fitness_offspring[i].length - 1; j++) {
                fitness_offspring[i][j] = offspring[i][j];
            }
        }
        //menghitung fitness semua kromosom
        for (int i = 0; i < fitness_chromosom.length; i++) {
            fitness_chromosom[i][fitness_chromosom[i].length - 1] = fitness(chromosom[i]);
        }
        for (int i = 0; i < fitness_offspring.length; i++) {
            fitness_offspring[i][fitness_offspring[i].length - 1] = fitness(offspring[i]);
        }
    }

    // menghitung nilai fitness
    public double fitness(int[] gen) {
        double hasil = 0;
        double time = 0; // variabel lama tempuh
        double penalty = 0;
        // menghitung nilai time tiap chromosom
        // menghitung waktu tempuh 
        int tt = 0;
        // looping wisata
        double timer = 5;
        int indek_wisata = 0;
        // looping keseluruhan chromosome hari pertama
        // untuk hari pertama hotel ke wisata ke-0
        //System.out.println("    HARI PERTAMA");
        String S_time = String.valueOf(time);
        String S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[0]]);
        time = tambah(S_time, S_time_hotel);
        //System.out.println("time awal = " + time);
        String S_timer = String.valueOf(timer);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println("timer awal = " + timer);
        if (gen[0] == 12) {
            S_timer = String.valueOf(timer);
            timer = tambah(S_timer, "5.00");
        } else {
            S_timer = String.valueOf(timer);
            timer = tambah(S_timer, "3.00");
        }
        //System.out.println("timer kedua = " + timer);
        indek_wisata++;
        for (int i = 0; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i]][gen[i + 1]]);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            //System.out.println("Timer 1" + " " + i + ": " + timer);
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("penalty = " + penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else if (timer < time_windows[gen[i]][0]) {
                timer = (time_windows[gen[i]][0]);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            indek_wisata++;
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("penalty = " + penalty);
            }
            //System.out.println("Timer 2" + " " + i + ": " + timer);
            // lihat waktu
            if (timer > 14.45) {
                break;
            }
        }
        //System.out.println("timer hari pertama 1 = " + timer);
        // kembali ke hotel
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println("Time hotel: " + S_time_hotel);
        //System.out.println("Timer: " + timer);
        // penalty
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
            //System.out.println("penalty = " + penalty);
        }
        //System.out.println("timer hari pertama = " + timer);
        // untuk hari kedua hotel ke wisata ke-1
        timer = 1;
        //System.out.println("    HARI KEDUA");
        //System.out.println("time awal = " + timer);
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        if (timer < time_windows[gen[indek_wisata]][0]) {
            timer = (time_windows[gen[indek_wisata]][0]);
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        } else {
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        }
        indek_wisata++;
        for (int i = indek_wisata; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            //System.out.println("Timer awal: " + timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i - 1]][gen[i]]);
            //System.out.println(S_time_wisata);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            //System.out.println("Timer 1" + " " + i + ": " + timer);
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
                //System.out.println("penalty = " + penalty);
            } else if (timer < time_windows[gen[i]][0]) {
                timer = (time_windows[gen[i]][0]);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("-penalty = " + penalty);
            }
            indek_wisata++;
            //System.out.println("Timer 2" + " " + i + ": " + timer);
            if (timer > 14.45) {
                break;
            }
        }
        // System.out.println("timer hari kedua 1 = " + timer);
        // kembali ke hotel
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        // penalty
        //System.out.println("timer hari kedua = " + timer);
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
            //System.out.println("penalty = " + penalty);
        }
        //System.out.println(indek_wisata + " " + gen[indek_wisata]);
        // untuk hari ketiga hotel ke wisata ke-
        //System.out.println("    HARI KETIGA");
        timer = 5;
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println("waktu awal: " + timer);
        if (timer < time_windows[gen[indek_wisata]][0]) {
            timer = (time_windows[gen[indek_wisata]][0]);
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        } else {
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        }
        //System.out.println("waktu akhir: " + timer);
        indek_wisata++;
        for (int i = indek_wisata; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i - 1]][gen[i]]);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            //System.out.println(S_time_wisata);
            //System.out.println("Timer 1" + " " + i + ": " + timer);
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
                //System.out.println("penalty = " + penalty);
            } else if (timer < time_windows[gen[i]][0]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][0]);
                timer = tambah(S_timer, String.valueOf(kurang(S_time_windows, S_timer)));
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("penalty = " + penalty);
            }
            //System.out.println("Timer 2" + " " + i + ": " + timer);
            indek_wisata++;
            if (timer > 14.45) {
                break;
            }
        }
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println(gen[indek_wisata-1]+" "+(indek_wisata-1)+" "+S_time_hotel);
        //System.out.println("Timer Hotel: "+timer);
        // penalty
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
        }
        double aa = indek_wisata;
        hasil = (1 / (1 + time)) + (penalty * (-1)) + (aa / 10);
        return hasil;
    }

    // menghitung nilai fitness
    public double fitness(int[] gen, boolean tanda) {
        double hasil = 0;
        double time = 0; // variabel lama tempuh
        double penalty = 0;
        // menghitung nilai time tiap chromosom
        // menghitung waktu tempuh 
        int tt = 0;
        // looping wisata
        double timer = 5;
        int indek_wisata = 0;
        // looping keseluruhan chromosome hari pertama
        // untuk hari pertama hotel ke wisata ke-0
        //System.out.println("    HARI PERTAMA");
        String S_time = String.valueOf(time);
        String S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[0]]);
        time = tambah(S_time, S_time_hotel);
        //System.out.println("time awal = " + time);
        String S_timer = String.valueOf(timer);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println("timer awal = " + timer);
        if (gen[0] == 12) {
            S_timer = String.valueOf(timer);
            timer = tambah(S_timer, "5.00");
        } else {
            S_timer = String.valueOf(timer);
            timer = tambah(S_timer, "3.00");
        }
        //System.out.println("timer kedua = " + timer);
        indek_wisata++;
        for (int i = 0; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i]][gen[i + 1]]);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            //System.out.println("Timer 1" + " " + i + ": " + timer);
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("penalty = " + penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else if (timer < time_windows[gen[i]][0]) {
                timer = (time_windows[gen[i]][0]);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            indek_wisata++;
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("penalty = " + penalty);
            }
            //System.out.println("Timer 2" + " " + i + ": " + timer);
            // lihat waktu
            if (timer > 14.45) {
                break;
            }
        }
        //System.out.println("timer hari pertama 1 = " + timer);
        // kembali ke hotel
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println("Time hotel: " + S_time_hotel);
        //System.out.println("Timer: " + timer);
        // penalty
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
            //System.out.println("penalty = " + penalty);
        }
        //System.out.println("timer hari pertama = " + timer);
        // untuk hari kedua hotel ke wisata ke-1
        timer = 1;
        //System.out.println("    HARI KEDUA");
        //System.out.println("time awal = " + timer);
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        if (timer < time_windows[gen[indek_wisata]][0]) {
            timer = (time_windows[gen[indek_wisata]][0]);
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        } else {
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        }
        indek_wisata++;
        for (int i = indek_wisata; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            //System.out.println("Timer awal: " + timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i - 1]][gen[i]]);
            //System.out.println(S_time_wisata);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            //System.out.println("Timer 1" + " " + i + ": " + timer);
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
                //System.out.println("penalty = " + penalty);
            } else if (timer < time_windows[gen[i]][0]) {
                timer = (time_windows[gen[i]][0]);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("-penalty = " + penalty);
            }
            indek_wisata++;
            //System.out.println("Timer 2" + " " + i + ": " + timer);
            if (timer > 14.45) {
                break;
            }
        }
        // System.out.println("timer hari kedua 1 = " + timer);
        // kembali ke hotel
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        // penalty
        //System.out.println("timer hari kedua = " + timer);
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
            //System.out.println("penalty = " + penalty);
        }
        //System.out.println(indek_wisata + " " + gen[indek_wisata]);
        // untuk hari ketiga hotel ke wisata ke-
        //System.out.println("    HARI KETIGA");
        timer = 5;
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println("waktu awal: " + timer);
        if (timer < time_windows[gen[indek_wisata]][0]) {
            timer = (time_windows[gen[indek_wisata]][0]);
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        } else {
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        }
        //System.out.println("waktu akhir: " + timer);
        indek_wisata++;
        for (int i = indek_wisata; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i - 1]][gen[i]]);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            //System.out.println(S_time_wisata);
            //System.out.println("Timer 1" + " " + i + ": " + timer);
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
                //System.out.println("penalty = " + penalty);
            } else if (timer < time_windows[gen[i]][0]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][0]);
                timer = tambah(S_timer, String.valueOf(kurang(S_time_windows, S_timer)));
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("penalty = " + penalty);
            }
            //System.out.println("Timer 2" + " " + i + ": " + timer);
            indek_wisata++;
            if (timer > 14.45) {
                break;
            }
        }
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println(gen[indek_wisata-1]+" "+(indek_wisata-1)+" "+S_time_hotel);
        //System.out.println("Timer Hotel: "+timer);
        // penalty
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
        }
        System.out.println("=>> " + time + " " + penalty + " " + indek_wisata);
        double aa = indek_wisata;
        hasil = (1 / (1 + time)) + (penalty * (-1)) + (aa / 10);
        return hasil;
    }

    public double fitness1(int[] gen, boolean tanda) {
        double hasil = 0;
        double time = 0; // variabel lama tempuh
        double penalty = 0;
        // menghitung nilai time tiap chromosom
        // menghitung waktu tempuh 
        int tt = 0;
        // looping wisata
        double timer = 5.0;
        int indek_wisata = 0;
        // looping keseluruhan chromosome hari pertama
        // untuk hari pertama hotel ke wisata ke-0
        System.out.println("                    HARI PERTAMA");
        System.out.println("-------------------------------------------------------");
        String S_time = String.valueOf(time);
        String S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[0]]);
        time = tambah(S_time, S_time_hotel);
        System.out.println("Memulai perjalanan dari hotel pukul \t\t: "+ String.format("%.2f", timer));
        //System.out.println("Waktu tempuh hotel -> obwis ke-1 \t\t: " + time);
        String S_timer = String.valueOf(timer);
        timer = tambah(S_timer, S_time_hotel);
        System.out.println("Tiba di obwis ke-1 pukul \t\t\t: " + String.format("%.2f", timer));
        if (gen[0] == 12) {
            S_timer = String.valueOf(timer);
            timer = tambah(S_timer, "5.00");
        } else {
            S_timer = String.valueOf(timer);
            timer = tambah(S_timer, "3.00");
        }
        System.out.println("Selesai kunjungan pada obwis ke-1 pukul \t: " + String.format("%.2f", timer));
        indek_wisata++;
        for (int i = 0; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i]][gen[i + 1]]);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            System.out.println("Mulai kunjungan pada obwis ke-"+ (i+2) + " pukul \t\t: " + String.format("%.2f", timer));
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                System.out.println("Total penalty : " + penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else if (timer < time_windows[gen[i]][0]) {
                timer = (time_windows[gen[i]][0]);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            indek_wisata++;
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                System.out.println("Total penalty = " + penalty);
            }
            System.out.println("Selesai Kunjungan pada obwis ke-" + (i+2) + " pukul \t: " + String.format("%.2f", timer));
            // lihat waktu
            if (timer > 14.45) {
                break;
            }
        }
        //System.out.println("Kunjungan obwis hari pertama 1 selesai pukul \t: " + timer);
        // kembali ke hotel
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println("Waktu tempuh obwis -> hotel \t\t\t: " + S_time_hotel);
        System.out.println("Tiba di hotel pukul \t\t\t\t: " + String.format("%.2f", timer));
        // penalty
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
            System.out.println("Total penalty : " + penalty);
        }
        //System.out.println("Perjalanan hari pertama selesai pukul : " + timer);
        // untuk hari kedua hotel ke wisata ke-1
        System.out.println("=======================================================");
        timer = 1;
        System.out.println("                     HARI KEDUA");
        System.out.println("-------------------------------------------------------");
        System.out.println("Memulai perjalanan dari hotel pukul\t\t: " + String.format("%.2f", timer));
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        System.out.println("Tiba di obwis ke-4 pukul\t\t\t: "+String.format("%.2f", timer));
        if (timer < time_windows[gen[indek_wisata]][0]) {
            timer = (time_windows[gen[indek_wisata]][0]);
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        } else {
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        }
        indek_wisata++;
        System.out.println("Selesai kunjungan pada obwis ke-4 pukul \t: " + String.format("%.2f", timer));
        for (int i = indek_wisata; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i - 1]][gen[i]]);
            //System.out.println(S_time_wisata);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            System.out.println("Mulai kunjungan pada obwis ke-"+ (i+1) + " pukul \t\t: " + String.format("%.2f", timer));
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
                System.out.println("penalty = " + penalty);
            } else if (timer < time_windows[gen[i]][0]) {
                timer = (time_windows[gen[i]][0]);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                System.out.println("penalty = " + penalty);
            }
            indek_wisata++;
            System.out.println("Selesai Kunjungan pada obwis ke-" + (i+1) + " pukul \t: " + String.format("%.2f", timer));
            if (timer > 14.45) {
                break;
            }
        }
        //System.out.println("timer hari kedua 1 = " + timer);
        // kembali ke hotel
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        // penalty
        System.out.println("Tiba di hotel pukul \t\t\t\t: " + String.format("%.2f", timer));
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
            System.out.println("penalty = " + penalty);
        }
        //System.out.println(indek_wisata + " " + gen[indek_wisata]);
        // untuk hari ketiga hotel ke wisata ke-
        System.out.println("=======================================================");
        System.out.println("                    HARI KETIGA");
        System.out.println("-------------------------------------------------------");
        timer = 5;
        System.out.println("Memulai perjalanan dari hotel pukul\t\t: "+String.format("%.2f", timer));
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        System.out.println("Tiba di obwis ke-8 pukul\t\t\t: " + String.format("%.2f", timer));
        if (timer < time_windows[gen[indek_wisata]][0]) {
            timer = (time_windows[gen[indek_wisata]][0]);
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        } else {
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        }
        System.out.println("Selesai kunjungan pada obwis ke-8 pukul\t\t: " + String.format("%.2f", timer));
        indek_wisata++;
        for (int i = indek_wisata; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i - 1]][gen[i]]);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            //System.out.println(S_time_wisata);
            System.out.println("Mulai kunjungan pada obwis ke-"+(i+1)+" pukul\t\t: " + String.format("%.2f", timer));
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
                System.out.println("penalty = " + penalty);
            } else if (timer < time_windows[gen[i]][0]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][0]);
                timer = tambah(S_timer, String.valueOf(kurang(S_time_windows, S_timer)));
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                System.out.println("penalty = " + penalty);
            }
            System.out.println("Selesai Kunjungan pada obwis ke-"+(i+1)+" pukul\t\t: " + String.format("%.2f", timer));
            indek_wisata++;
            if (timer > 14.45) {
                break;
            }
        }
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println(gen[indek_wisata - 1] + " " + (indek_wisata - 1) + " " + S_time_hotel);
        System.out.println("Tiba di hotel pukul\t\t\t\t: " + String.format("%.2f", timer));
        // penalty
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
        }
        System.out.println("=======================================================");
        System.out.println("=>> " + time + " " + penalty + " " + indek_wisata);
        double aa = indek_wisata;
        hasil = (1 / (1 + time)) + (penalty * (-10)) + (aa / 10);
        return hasil;
    }

    // operasi perjumlahan waktu
    public double tambah(String a, String b) {
        double hasil = 0;
        if (Double.valueOf(b) >= 0 && Double.valueOf(a) >= 0) {
            String string_a = (a);
            double[] array_a = rubah(string_a);
            String string_b = (b);
            double[] array_b = new double[2];
            array_b = rubah(string_b);
            double menit = array_a[1] + array_b[1];
//            System.out.println(array_a[1]+" + "+array_b[1]+"-> "+menit);
            // hitung menit dan sisanya
            double jam_menit = 0, sisa_menit = 0;
            sisa_menit = (menit % 60);
            jam_menit = (menit - sisa_menit) / 60;
            // hitung jam
            hasil = (array_a[0] + array_b[0]) + jam_menit;
            double menit_jam = sisa_menit / 100;
            hasil += menit_jam;
        } else if (Double.valueOf(a) >= 0 && Double.valueOf(b) < 0) {
            String s_a = (a);
            String s_b = String.valueOf(Math.abs(Double.valueOf(b)));
            hasil = kurang(s_a, s_b);
        } else if (Double.valueOf(a) < 0 && Double.valueOf(b) >= 0) {
            String s_a = String.valueOf(a);
            String s_b = String.valueOf(Math.abs(Double.valueOf(b)));
            hasil = kurang(s_b, s_a);
            hasil *= -1;
        } else {
            hasil = tambah(String.valueOf(Math.abs(Double.valueOf(a))), String.valueOf(Math.abs(Double.valueOf(b))));
            hasil *= -1;
        }
        return hasil;
    }

    // operasi pengurangan waktu
    public double kurang(String a, String b) {
        double hasil = 0;
        String string_a = (a);
        double[] array_a = rubah(string_a);
        String string_b = (b);
        double[] array_b = rubah(string_b);
        double menit = array_a[1] - array_b[1];
        double jam_menit = 0, sisa_menit = 0;
        if (menit < 0) {
            jam_menit = menit * -1;
        }
        jam_menit = 60 - jam_menit;
        jam_menit /= 100;
        // hitung jam
        hasil = (array_a[0] - array_b[0]);
        if (menit < 0) {
            hasil = hasil - 1;
            hasil += jam_menit;
        } else {
            menit /= 100;
            hasil += menit;
        }
        return hasil;
    }

    public double[] rubah(String string_a) {
        //System.out.println(string_a);
        char[] pp = new char[string_a.length()];
        for (int i = 0; i < string_a.length(); i++) {
            pp[i] = string_a.charAt(i);
        }
        for (int i = 0; i < pp.length; i++) {
            // kondisi perkalian 10
            if (pp[i] == '.') {
                if (i == 2) {
                    String a = String.valueOf(pp[i + 1]);
                    if (Double.valueOf(a) > 0) {
                        if (pp.length == 5) {
                        } else if (pp.length == 4) {
                            char[] new_pp = pp;
                            pp = new char[pp.length + 1];
                            for (int j = 0; j < new_pp.length; j++) {
                                pp[j] = new_pp[j];
                            }
                            pp[i + 2] = '0';
                        }
                    }
                } else if (i == 1) {
                    String a = String.valueOf(pp[i + 1]);
                    if (Double.valueOf(a) > 0) {
                        if (pp.length == 4) {
                        } else if (pp.length == 3) {
                            char[] new_pp = pp;
                            pp = new char[pp.length + 1];
                            for (int j = 0; j < new_pp.length; j++) {
                                pp[j] = new_pp[j];
                            }
                            pp[i + 2] = '0';
                        }
                    }
                }
            }
        }
        for (int i = 0; i < pp.length; i++) {
            if (pp[i] == '.') {
                pp[i] = '-';
            }
        }
        string_a = "";
        for (int i = 0; i < pp.length; i++) {
            string_a += pp[i];
        }
        String[] array_aa = string_a.split("-");
        double[] array_double = new double[array_aa.length];
        for (int i = 0; i < array_aa.length; i++) {
            array_double[i] = Double.valueOf(array_aa[i]);
        }
        //System.out.println(Arrays.toString(array_double));
        return array_double;
    }
}