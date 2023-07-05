# :triangular_flag_on_post: Apache Kafka

![](/images/a-kafka.png)

<b> Apache Kafka, yüksek performanslı, dağıtık bir veri akışı platformudur. Birçok uygulama arasında gerçek zamanlı veri akışı ve olay işleme sağlamak için kullanılır. Kafka, büyük ölçekli veri işleme, günlük kayıtları, uygulama metrikleri, makine sensör verileri gibi verilerin toplanması, depolanması ve dağıtılmasında etkili bir araçtır.</b>

## 🎯 **Topic ve Partition**

**Topic verilerin gönderilip alındığı veri kategorisinin adıdır. Kullanıcı tarafından isimlendirilirler. Bir Kafka cluster’ında binlerce topic olabilir.**

**Topic’ler partition’lara ayrılırlar. Partition’lar 0’dan başlayarak artan sayıda devam eder. Topic’de 1 partition oluşturulabileceği gibi senaryoya göre bin partition da oluşturulabilir. Topic yaratırken verdiğimiz partition sayısını sonradan değiştirebiliriz.**

**Veri bir kez bir partition’a yazıldıktan sonra bir daha değiştirilemez. (immutability)**

**Partition’ların 0’dan başlayarak artan sayıda giden ID’leri vardır. Bunlara offset denir. Offset partition’la beraber bir anlam ifade eder, 5. offset diyemeyiz mesela, partition 2’nin 5. offset’i diyebiliriz. Her partition’da farklı sayıda offset olabilir.**

![](/images/b-kafka.png)

**Partition’lar kendi içinde sıralıdır. Partition 2 için 7. offset’deki veri 8. offset’den önce yazılmıştır. Ama Partition 1’deki 5. offset’deki veri ile Partition 2’deki 5. offset’deki veriyi kıyaslayamayız, hangisinin daha önce yazıldığını bilme şansımız yoktur.**

**Partition sayısı değiştirilmediği sürece Kafka, aynı key ile gönderilen her mesajın aynı partition’a düşeceğini garanti eder. Partition sayısı değiştirilirse bu garanti ortadan kalkar. Key ile veri göndermenin avantajı o veriye daha sonra aynı key ile sıralı bir şekilde erişmektir. Key ile gönderilmeyen her veri partition’lara rastgele bir şekilde dağıtılır.**

**Partition sayısı 1 seçilirse consumer verilere producer’ın ilettiği sırada erişir.**

**Kafka veriyi belli bir süre (varsayılan 1 hafta) muhafaza eder, daha sonra veri silinir. Ancak eski veri silinse de, partition’a yeni veriler gelince offset değeri kaldığı yerden devam eder, bir daha sıfıra dönmez.**

## 🎯 **Broker**

**Broker’lar Kafka Cluster’ı oluşturan sunuculardır. Her broker birer sayıdan oluşan ID ile tanımlıdır. Kafka Cluster oluşturulurken broker sayısı üç gibi bir sayıyla başlar, gereksinim arttıkça ilave edilir. Yüzün üstünde broker koşturan sistemler de vardır.**

**Bağlandığımız sunucu bootstrap broker adını alır. Bootstrap broker’a bağlanınca tüm broker’ların bilgisinin tutulduğu metadata sayesinde diğer broker’ların adreslerine erişilir. Tüm kafka cluster’a bağlanmak için sadece bootstrap broker’a bağlanmak yeterlidir.**

**Kafka dağıtık yapıda olduğu için tek bir broker topic’in tamamını alamaz. Her broker topic’in belli kısımlarını (partition) alır. Brokerlara partition’ların dağıtımı, eşit şekilde yapılmaya çalışılır.**

![](/images/c-kafka.png)

<b> Örneğin 101, 102 ve 103 nolu üç broker’ımız olsun. Topic A üç partition’dan oluştuğu ve üç broker’ımız olduğu için her broker Topic A’ya ait birer partition’a sahip olur. Dikkatinizi çekerse partition’lar broker’lara sıralı bir şekilde dağıtılmıyor; 101 nolu broker’a 2. partition düşerken, 102’ye 0. partition, 103’e 1. partition düşebiliyor. Topic B’ye ait 2 partition olduğu için Broker 101 ve Broker 102’ye birer partition düşüyor, Broker 103 boşta kalıyor. (Yine rastgele şekilde Broker 103’e partition atansaydı Broker 102 de boşta kalabilirdi.) Topic C’ye ait 4 partition iki broker’a birer tane, bir broker’a iki tane olacak şekilde dağıtılır. </b>

## 🎯 **Producer**

**Kafka'da producer, veri akışının yayınlandığı tarafı temsil eder. Producer, belirli bir başlığa veri gönderir ve bu veri, Kafka kümesindeki brokerlara iletilir. Veriler, belirli bir başlık ve bölüm kombinasyonuyla ilişkilendirilerek yayınlanır. Producer, Kafka kümesine veri göndermek için belirli bir konfigürasyon ve Kafka brokerlarına erişim bilgileriyle yapılandırılır.**

**Publisher (yayımcı) pozisyonundadırlar. Veriyi topic’e key ile ve key’siz olmak üzere iki farklı yolla gönderebilirler.**

**Key ile gönderme durumunda ilk gönderilen mesaj hangi partition’a gittiyse aynı key ile gönderilen diğer mesajlar da aynı partition’a gönderilirler. Belli bir sınıftaki verimize consumer tarafından sıralı bir şekilde ulaşılmasını istiyorsak key ile gönderme tercih edilir.**

**Key’siz gönderme yönteminde Kafka iş yükünü dağıtmak için (load balancing) sıralı bir şekilde (round robin) gönderecektir. Bu durumda verimize sonradan sıralı bir şekilde ulaşamayız.**

**Producer ile mevcut olmayan bir topic’e veri göndermeye çalışırsak Kafka önce bu topiği oluşturur. Ancak bu konudaki best practice, topic’i gereksinimlere göre replication ve partition belirterek kendimizin oluşturmasıdır.**


## 🎯 **Consumer**

**Kafka'da consumer, veri akışını tüketen tarafı temsil eder. Consumer, belirli bir başlık ve bölüm kombinasyonunu izleyerek veriyi okur ve işler. Consumer'lar, Kafka kümesindeki brokerlardan verileri alır ve belirli bir konfigürasyon ve abonelik ayarlarıyla yapılandırılır.**

**Subscriber (abone) pozisyonundadırlar.**

**Partition’lardan paralel şekilde okuma yapar. Partition bazında sırayla okur. Partition’lar arası sırayla okuma yapamaz. İki partition’dan okuma yapıyorsa birinde 5. indexde diğerinde 20. indexde olabilir.**

**Consumer’lar verileri consumer group’lar halinde okur. Tek de olsa her consumer’ın bir grubu vardır. Grubu biz oluşturmazsak Kafka otomatik olarak oluşturur.**

**Gruptaki her consumer belli partition’lardan okuma yapar. Mesela topic’imizde 3 partition varsa:**

- **3 consumer’ımız varsa her consumer birer partition’dan okuma yapar.**
- **2 consumer’ımız varsa biri tek partition’dan, biri kalan iki partition’dan okuma yapar.**
- **4 consumer’ımız varsa biri inaktif olur diğer üçü birer partition’dan okuma yapar. Gruptaki bir consumer’ın çökmesi gibi durumlara karşı bazen bu şekilde partition sayısından fazla consumer kullanıldığı olur.**

## 🎯 **Topic Replication**

**Dağıtık sistemlerin avantajlarından biri, sunuculardan biri offline olsa bile sistemin sürdürebilir durumda olmasıdır. Kafka’da da replica’lar sayesinde sistemin devam etmesi ve veri kaybının önüne geçilmesi sağlanır.**

**Replication ile topic’lerin her partition’u birden fazla sunucuda saklanır. Bu sunuculardan biri leader’dir, diğerleri ISR (in-sync replica) denilen kopyasıdır. ISR’ler veriyi senkronize eden, kopyasını tutan pasif sunuculardır, veri alışverişi leader üzerinden sağlanır. Leader ve ISR’ler zookeeper tarafından belirlenir. Replication’lar topic oluşturulurken replication-factor parametresi ile belirtilirler.**

![](/images/d-kafka.png)

**replication factor’ü 3 ve partition sayısı 4 olan Topic 1 görünmektedir. Partition 1 için 1 nolu broker leader 2 ve 3 nolu broker’lar ISR pozisyonundadır. Partition 2 içinse 3 nolu broker leader 1 ve 4 nolu broker’lar ISR’dir. Bu şekilde her partition için lider ve replica’lar tüm broker’larda dağıtık olarak tutulurlar.**

**Her partition’un leader’i farklı broker’larda olabilir. Bir partition için aynı anda sadece bir tane leader vardır. Örneğin partition 1 için leader olan broker 1 offline olursa veya çökerse broker 2 veya broker 3 lider pozisyona geçecektir. Broker 1 ayağa kalkınca tekrar leader olacaktır.**

## :pushpin: Kafka Bağımlılığı 
```xml
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>
```
