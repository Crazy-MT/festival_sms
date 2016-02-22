package com.maotong.city;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //"Temirtau","Pescara",
    private String[] cityList = {"Temirtau","Kyiv", "New%20Delhi", "Buenos%20Aires", "Moscow", "Paris", "London", "Milan", "Osaka", "Madrid", "Berlin", "Rome", "New%20York", "Seoul", "Bangkok", "Sao%20Paulo", "Lahore", "Warsaw", "Yokohama", "Saint%20Petersburg", "Hong%20Kong", "Shinjuku", "Kolkata", "Kuala%20Lumpur", "Chicago", "Istanbul", "Tehran", "Bucharest", "Mexico%20City", "Bengaluru", "Los%20Angeles", "Houston", "Nagoya", "Santiago", "Minato", "Ufa", "Karachi", "Krasnodar", "Cairo", "Budapest", "Barcelona", "Tel%20Aviv-Yafo", "Hanoi", "Pune", "Jakarta", "Dallas", "Atlanta", "Toronto", "Yekaterinburg", "Sydney", "Hamburg", "Cologne", "Prague", "Riyadh", "Ankara", "Melbourne", "Mumbai", "Rio%20de%20Janeiro", "Belgrade", "Ho%20Chi%20Minh%20City", "Athens", "Surabaya", "Munich", "San%20Francisco", "Minsk", "Vienna", "Washington", "Fukuoka", "Bratislava", "Novosibirsk", "Sofia", "Chennai", "Islamabad", "Chandigarh", "Zagreb", "Quezon%20City", "Hyderabad", "Jeddah", "Seattle", "Amsterdam", "Busan", "Sandton", "Bogota", "Monterrey", "Valencia", "Lisbon", "Dubai", "Frankfurt", "Casablanca", "Irkutsk", "Krakow", "Colombo", "Detroit", "Dublin", "Rawalpindi", "Cape%20Town", "Belo%20Horizonte", "Vladivostok", "Krasnoyarsk", "Phoenix", "Brisbane", "Lucknow", "Izmir", "San%20Antonio", "Naples", "San%20Diego", "Ahmedabad", "Boston", "Kharkiv", "La%20Victoria", "Pretoria", "Denver", "Stockholm", "Kazan", "Myrtle%20Beach", "Stuttgart", "Nizhniy%20Novgorod", "Kuwait%20City", "Dusseldorf", "Zhongli%20District", "Cordoba", "Vancouver",  "Lagos", "Seville", "Poznan", "Singapore", "Zurich", "Philadelphia", "Tunis", "Sapporo", "Dhaka", "Tashkent", "Tbilisi", "Calgary", "Turin", "Skopje", "Shanghai", "Columbus", "Wroclaw", "Dortmund", "Almaty", "Porto%20Alegre", "Amã", "Montreal", "Rosario", "Brussels", "Nashville", "Guadalajara", "Florence", "Beirut", "Portland", "Perm", "Voronezh", "Charlotte", "Baghdad", "Saratov", "Chisinau", "Helsinki", "Riga", "Jacksonville", "Dresden", "Curitiba", "Gdansk", "Leipzig", "Odessa", "Bologna", "Alexandria", "Medan", "Perth", "Tirana", "Orlando", "Dnepropetrovsk", "Nuremberg", "Chelyabinsk", "Durban", "Miami", "Thessaloniki", "Kyoto", "Saitama", "Samara", "Faisalabad", "Jaipur", "Copenhagen", "Hamedan", "Minneapolis", "Vilnius", "Goiania", "Makati", "Rostov-on-Don", "Beijing", "Algiers", "Auckland", "Paradise", "Kathmandu", "Baku", "Peshawar", "Porto", "Sendai", "Hanover", "Merida", "Tyumen", "Sacramento", "Sulaymaniyah", "Adana", "Ljubljana", "Birmingham", "Lviv", "Mendoza", "Shibuya", "Caracas", "St.%20Louis", "Timisoara", "Palermo", "Khulna", "Chiba", "Phnom%20Penh", "Multan", "Katowice", "Gujranwala", "Kraljevo", "Hiroshima", "Yerevan", "Zaporizhia", "Damascus", "Omsk", "Essen", "Doha", "Incheon", "Adelaide", "Indore", "Catania", "Laval", "Austin", "Ann%20Arbor", "Giza", "Salt%20Lake%20City", "Salisbury", "Sochi", "Tomsk", "Tallinn", "Oslo", "Taoyuan%20District", "Indianapolis", "Malaga", "Bishkek", "Bari", "Ussuriysk", "Plano", "Kaluga", "Antalya", "Campinas", "Madison", "Guangzhou", "Cambridge", "Sarajevo", "Bursa", "Novi%20Sad", "Manchester", "Bilbao", "Ahvaz", "Abu%20Dhabi", "Khabarovsk", "Pyatigorsk", "Cebu%20City", "Sialkot", "Fortaleza", "San%20Jose", "Lyon", "Toulouse", "Daegu", "Pancevo", "Szczecin", "Makassar", "San%20Salvador", "Kobe", "Brasilia", "Valladolid", "Bremen", "Grand%20Rapids", "Lodz", "Salvador", "Muscat", "Kaliningrad", "Edmonton", "Surgut", "Zaragoza", "Omaha", "Rotterdam", "Athens", "Quito", "Navoi", "Tabriz", "Ludhiana", "Greenville", "Ottawa", "Richmond", "Mississauga", "Leeds", "Padua", "Asuncion", "Jammu", "Nicosia", "Cluj-Napoca", "Bhopal", "Chiyoda", "Mar%20del%20Plata", "Manama", "Kansas%20City", "Varna", "Raleigh", "Winnipeg", "Medellin", "Milwaukee", "Manila", "La%20Plata", "San%20Miguel%20de%20Tucuman", "Cali", "Duisburg", "Venice", "Karlsruhe", "A%20Coruna", "Donetsk", "Edinburgh", "Amritsar", "Chuo", "Nis", "Birmingham", "Neihu%20District", "Da%20Nang", "Volgograd", "Virginia%20Beach", "Macau", "Brasov", "Genoa", "Setagaya", "Johannesburg", "San%20Luis%20Potosi", "Vicenza", "Nairobi", "Dammam", "Recife", "Valencia", "Kingston", "Mexicali", "Tampa", "Shenzhen", "Verona", "Tula", "Bahia%20Blanca", "Memphis", "Hermosillo", "San%20Jose", "Brno", "New%20Cairo%20City", "Gimhae-si", "Jalandhar", "Sharjah", "Centurion", "Iasi", "Izhevsk", "Ontario", "Bochum", "Munster", "Vitsyebsk", "Naberezhnye%20Chelny", "Esfahan", "Port-of-Spain", "Santiago%20de%20Queretaro", "Arlington", "San%20Juan", "Louisville", "Ciudad%20Juarez", "Brampton", "Bobruysk", "Cincinnati", "Montevideo", "Colorado%20Springs", "Santo%20Domingo", "Miami%20Beach", "Mannheim", "Bristol", "Glasgow", "Guatemala%20City", "Patna", "Gomel", "Fort%20Worth", "Gothenburg", "Johor%20Bahru", "Ribeirao%20Preto", "Rochester", "Halle%20(Saale)", "The%20Hague", "Brescia", "Diyarbakir", "Astana", "Mykolaiv", "Bordeaux", "Las%20Vegas", "Santa%20Cruz%20de%20la%20Sierra", "Alicante", "Lublin", "Wuhan", "Tripoli", "El%20Paso", "Constanta", "Kochi", "Chengdu", "Clarksville", "Albuquerque", "Mainz", "Maringa", "Concepcion", "Lille", "Oradea", "Waterloo", "Kemerovo", "Bahawalpur", "Xi'an", "Sheffield", "Granada", "Kosice", "Suzhou", "Puebla", "Vinnytsia", "Albany", "Yaroslavl", "Okayama", "Surrey", "Plovdiv", "Barranquilla", "Bielefeld", "Bandung", "Resistencia", "Liverpool", "Port%20Louis", "Springfield", "Christchurch", "Daejeon", "Kherson", "Ghent", "Penza", "Chihuahua", "Gwangju", "Nantes", "Johnstown", "Saint%20Paul", "Niigata", "Bydgoszcz", "Shiraz", "Xalapa", "Hai%20Phong", "Bryansk", "Mashhad", "Salta", "Lecce", "Newark", "Kuching", "Dar%20es%20Salaam", "Tolyatti", "Lipetsk", "Kryvyi%20Rih", "Ulyanovsk", "Villahermosa", "Zhudong%20Township", "Antwerp", "Mountain%20View", "Stavropol", "Leicester", "Suwon-si", "Sao%20Bernardo%20do%20Campo", "Kassel", "Nottingham", "New%20Haven", "Torreon", "Tijuana", "Posadas", "Palma", "Freiburg", "Tucson", "Boise", "Bergamo", "Florianopolis", "Rennes", "Leon", "Marseille", "George%20Town", "Podgorica", "Roodepoort", "Little%20Rock", "Hyderabad", "Neuquen", "Toluca", "Guayaquil", "Semey", "Brcko", "Udine", "Woodbridge%20Township", "Tegucigalpa", "Yogyakarta", "Southfield", "Rabat", "Gijon", "Chemnitz", "Kula", "Oklahoma%20City", "Arak", "Erfurt", "Ashgabat", "Barnaul", "Simferopol", "Sargodha", "Heroica%20Veracruz", "Sao%20Jose%20dos%20Campos", "Accra", "Jersey%20City", "Balashikha", "Montpellier", "Augsburg", "Murcia", "Newcastle%20upon%20Tyne", "Campo%20Grande", "Kirov", "Chongqing", "Podolsk", "Konya", "Noida", "Bonn", "Uberlandia", "Wuppertal", "Vientiane", "Quetta", "Kiel", "Tver", "Tampico", "Darmstadt", "Orenburg", "Yuzhno-Sakhalinsk", "Brunswick", "Chernivtsi", "Nanjing", "Hangzhou", "Hamamatsu", "Strasbourg", "Culiacan", "Murmansk", "Irvine", "Cherkasy", "Santa%20Fe", "Treviso", "Cheboksary", "Zilina", "Durham", "Split", "Markham", "Ryazan", "Belgorod", "Mardan", "Vigo", "Rasht", "Spokane", "Sidi%20Bel%20Abbes", "Tiraspol", "Shymkent", "Londrina", "Pittsburgh", "Palos%20Heights", "Hamilton", "Cleveland", "Ipoh", "Baltimore", "Norilsk", "Szeged", "Urmia", "Cancun", "Davao%20City", "Kawasaki", "Seongnam-si", "Kaunas", "Bhubaneshwar", "Presidente%20Prudente", "Rimini", "Cagliari",  "Reynosa", "Macon", "Wurzburg", "Aguascalientes", "Oldenburg", "Novokuznetsk", "Arkhangelsk", "Liege", "Ostrava", "Santos", "Nice", "Poltava", "Graz", "Geneva", "Sumy", "Rzeszow", "Osnabruck", "Petaling%20Jaya", "Al%20Khobar", "Modena", "London", "Chita", "Bridgewater", "Regensburg", "Luxembourg%20City", "Cardiff", "Buffalo", "Monza", "Lucca", "Joinville", "Utrecht", "Cuernavaca", "Wiesbaden", "Fresno", "New%20Orleans", "Pamplona", "Sioux%20City", "Ulaanbaatar", "Ulsan", "Chernihiv", "Aurora", "Ivano-Frankivsk", "Aachen", "Taganrog", "Panama", "Tianjin", "Toledo", "Mansoura", "Fremont", "Galati", "Long%20Beach", "Southampton", "Bitola", "Kota%20Kinabalu", "Cordoba", "Yakutsk", "Changhua%20City", "Gurgaon", "Rivne", "Corrientes", "Glasgow", "Craiova", "Cuautitlan%20Izcalli", "Debrecen", "Riverside", "Targu%20Mures", "Temuco", "Florida%20City", "Khmelnytskyi", "Goyang-si", "Yongin-si", "Nizhny%20Tagil", "Nancy", "Eugene", "Arad", "Marrakesh", "Ploiesti", "Morelia", "Oviedo", "Honolulu", "Las%20Palmas%20de%20Gran%20Canaria", "Kanazawa", "Coimbatore", "Kumamoto", "Tarragona", "Santo%20Andre", "Changsha", "Makhachkala", "Fes", "Maputo", "Burgas", "Gaziantep", "Oryol", "Sibiu", "Utsunomiya", "Parma", "Navi%20Mumbai", "Palembang", "Qingdao", "Bialystok", "Kursk", "Mesa", "Guwahati", "Yulee", "Dijon", "Banja%20Luka", "Sevastopol", "Monchengladbach", "Ciudad%20Mendoza", "Belfast", "Slough", "Magdeburg", "Abidjan", "Vladimir", "Shah%20Alam", "Gainesville", "Zhengzhou", "Saskatoon", "Male", "Kent", "Denpasar", "Semarang", "Caxias%20do%20Sul", "Ivanovo", "Croydon", "Saarbrucken", "Trier", "Vancouver", "Tuxtla%20Gutierrez", "Pitesti", "Bakersfield", "Kabul", "Greensboro", "Pisa", "Astrakhan", "Sabzevar", "Montgomery", "Kirovohrad", "Brighton", "Zhubei%20City", "Paderborn", "Knoxville", "Quilmes", "Zapopan", "Kayseri", "Bacoor%20City", "Mytishchi", "Girona", "Burnaby", "Coventry", "Tulsa", "Ulm", "Northampton", "Como", "Grenoble", "Mueang%20Chiang%20Mai%20District", "Anchorage", "Carson", "Salerno", "Provo", "Koblenz", "Rijeka", "Rouen", "Celaya", "Richmond", "Pecs", "Canberra", "Yilan%20City", "Stoke-on-Trent", "Miskolc", "Sorocaba", "Shenyang", "Ulan-Ude", "Clermont-Ferrand", "Tours", "Tanta", "Hays", "Pingtung%20City", "Sukkur", "Mariupol'", "Anaheim", "Maribor", "Beckett%20Ridge", "Abuja", "Cuiaba", "Douliu%20City", "Norwich", "Aarhus", "Mersin", "Santander", "Poza%20Rica%20de%20Hidalgo", "Sioux%20Falls", "Kostroma", "Yuanlin%20Township", "Malacca", "Khartoum", "Eindhoven", "Valparaiso", "Giessen", "Zhytomyr", "Meknes", "Annaba", "Richmond%20Hill", "Windsor", "Tambov", "Kremenchuk", "Addis%20Ababa", "Funabashi", "Kurgan", "Wichita", "Agadir", "Vitoria-Gasteiz", "Kaiserslautern", "Salamanca", "Groningen", "Vologda", "Krefeld", "Vila%20Nova%20de%20Gaia", "Manaus", "Leon", "Roanoke", "Veliky%20Novgorod", "Gyor", "Surat", "Norfolk", "Lincoln", "Getzville", "Quebec%20City", "Syktyvkar", "Chiayi%20City", "Ansan-si", "Linz", "Saransk", "Pavlodar", "Luhansk", "Smolensk", "Tuzla", "Everett", "Amiens", "Ternopil", "San%20Luis", "Stockton", "Oxford", "Maracaibo", "Suceava", "Pachuca", "Nanning", "Petropavlovsk-Kamchatskiy", "Reno", "Osijek", "Bratislava", "Clearwater", "Kuantan", "Nerima", "Cambridge", "Westland", "Charlottesville", "Korolyov", "San%20Pedro%20Sula", "Gifu", "Pilsen", "Natal", "Cosenza", "tp.%20Dja%20Lat", "Harbin", "Mount%20Laurel", "Naha", "Fort%20Wayne", "Mostar", "Saint%20Julian's", "Heilbronn", "Nagano", "Nuevo%20Laredo", "Ciudad%20Nezahualcoyotl", "Osasco", "Beaverton", "Castellon%20de%20la%20Plana", "Anyang-si", "Dushanbe", "Reims", "Gimpo-si", "Biysk", "Vaughan", "Malmo", "Bucaramanga", "Harare", "Heidelberg", "Santiago%20De%20Los%20Caballeros", "Shaker%20Heights", "Fayetteville", "Lawrenceville", "Almetyevsk", "Jinan", "Bellevue", "Gdynia", "Canoas", "Aberdeen", "Samsun", "Magnitogorsk", "San%20Sebastian", "Fuzhou", "Morioka", "Banska%20Bystrica", "Troy", "Rochester", "Nara", "Khimki", "Botosani", "Nakhon%20Pathom", "Bielsko-Biala", "Tampere", "Matsuyama", "Shizuoka", "Kerman", "Caen", "Petrozavodsk", "Al%20Ain", "Prato", "Kagoshima", "Wellington", "Louisville", "Vadodara", "Blumenau", "Czestochowa", "Davie", "Alor%20Setar", "Oakland", "Kecskemet", "Bucheon-si", "Eskisehir", "Yoshkar-Ola", "Bien%20Hoa", "Cherepovets", "Oberhausen", "Trento", "Hefei", "Santa%20Maria", "Barquisimeto", "Bay%20Lake", "Dongguan", "Monclova", "Tangerang", "Bournemouth", "Lexington", "Toyama", "Gelsenkirchen", "Durango", "Haifa", "Regina", "Vila%20Velha", "Greensburg", "Zagazig", "Nizhnevartovsk", "Babol", "Xiamen", "Almeria", "Jackson", "Tangier", "Shtip", "Huelva", "Yigitali%20Koyu", "Nuestra%20Senora%20de%20La%20Paz", "Hwaseong-si", "Kitchener", "Qom", "Plantation", "Blacksburg", "Cary", "Burgos", "Arjona", "Winston-Salem", "Portsmouth", "Blagoveshchensk", "Belem", "Atyrau", "Santa%20Cruz%20de%20Tenerife", "Reading", "Szekesfehervar", "Sagamihara", "Poitiers", "Sakarya", "Lleida", "Akita", "Budva", "Olsztyn", "Denizli", "Bekasi", "Sao%20Jose%20do%20Rio%20Preto", "Tsukuba", "Kielce", "Sunnyvale", "Jeonju-si", "Derby", "Ruse", "Lusaka", "Kitakyushu", "Satu%20Mare", "Koto", "Taito", "Buzau", "Rancagua", "Msida", "Basel", "Arlington", "Innsbruck", "Olomouc", "Des%20Moines", "Saint%20Denis", "Bern", "Toledo", "Chittagong", "Mito", "Lyubertsy", "Cheongju-si", "Preston", "Bacau", "Huancayo"};

     //private String[] cityList = {"Pescara","Shtip", "Paris","Dnepropetrovsk", "Shtip"};//, "Yigitali%20Koyu","New%20Delhi","Moscow","Olomouc", "Des%20Moines", "Saint%20Denis"};
    private static final String CITY_ID_URL = "http://accuwxturbo.accu-weather.com/widget/accuwxturbo/city-find.asp?location=";
    private static final String CITY_BEAN_URL_FRONT = "http://accuwxturbo.accu-weather.com/widget/accuwxturbo/weather-data.asp?location=cityId:";
    private static final String CITY_BEAN_URL_BACK = "&metric=1&langid=13&partner=androidflagshipA";
    private static final String TAG = "MainActivity";
    private static final String EMPTY = "empty"; //当借口无返回数据时
    private static final String JSON_CITY_FILE_NAME = "AmberWeatherCityAndJson.txt";
    private static final String JSON_FILE_NAME = "AmberWeatherCityJson.txt";
    private static final String CITY_FILE_NAME = "AmberWeatherCity.txt";
    private EditText mCityText;
    private List<CityBean> mCityBeanList;
    private StringBuilder mAllCity = new StringBuilder();
    private long startTime;
    private long endTime;
    private int cityIdNo = 0 ;
    private int cityNo = 0 ;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    CityBean cityBean = (CityBean) msg.obj;
                    new CityBeanAsyncTask(cityBean).execute(CITY_BEAN_URL_FRONT + cityBean.getCityId() + CITY_BEAN_URL_BACK);
                    cityIdNo++;
                    mCityText.setText(cityIdNo + "/" + cityList.length + cityBean.getCityId());
                    break;
                case 1:
                    String json = new CityBeanListToJson(mCityBeanList).getJson();
                    mAllCity.append(json);

                    StepComparator comparator = new StepComparator();
                    Collections.sort(mCityBeanList, comparator);
                    //
                    for (int i = 0; i < mCityBeanList.size(); i++) {
                        CityBean cityBean1 = mCityBeanList.get(i);
                        mAllCity.append(i + " ");
                        mAllCity.append(cityBean1.toString());
                        mAllCity.append("\n");
                    }

                    writeToFile(json, JSON_FILE_NAME);
                    writeToFile(mAllCity.toString(), JSON_CITY_FILE_NAME);
                    mCityText.setText(mAllCity);
                    endTime = System.currentTimeMillis();
                    long minuteTime = (endTime - startTime)/1000/60;
                    Toast.makeText(getApplicationContext(),"运行时长：" + minuteTime , Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startTime = System.currentTimeMillis();
        initView();
        initData();

    }

    private void initData() {
        Log.e(TAG, cityList.length + "城市数量");
        mCityBeanList = new ArrayList<>();

        for (int i = 0; i < cityList.length; i++) {
            new CityIdAsyncTask().execute(CITY_ID_URL, cityList[i]);
        }

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCityText = (EditText) findViewById(R.id.text_city);
    }

    class CityIdAsyncTask extends AsyncTask<String, Void, CityBean> {


        @Override
        protected CityBean doInBackground(String... params) {
            return getCityId(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(CityBean cityBeen) {
            //mCityBeanList.add(cityBeen);
            Message message = new Message();
            message.what = 0;
            message.obj = cityBeen;
            mHandler.sendMessage(message);
        }

        private CityBean getCityId(String param, String cityName) {
            CityBean cityBean = null;
            try {
                InputStream inputStream = new URL(param + cityName).
                        openConnection().getInputStream();
                NewsParser parser = new PullCityIdParser();
                try {
                    cityBean = parser.parser(inputStream);
                    if (TextUtils.isEmpty(cityBean.getCityId())) {
                        cityBean.setCityId(EMPTY);
                        //Log.e("mt", cityBean.getCityId());
                    }
                    cityBean.setCityName(cityName);

                } catch (Exception e) {
                    Log.e(TAG,e.toString());
                    e.printStackTrace();
                }
            } catch (IOException e) {
                Log.e(TAG,e.toString());
                e.printStackTrace();
            }
            return cityBean;
        }
    }

    class CityBeanAsyncTask extends AsyncTask<String, Void, CityBean> {

        private CityBean cityBean = null;

        public CityBeanAsyncTask(CityBean cityBean) {
            this.cityBean = cityBean;
            Log.e(TAG,this.cityBean.toString());
            if (EMPTY.equals(cityBean.getCityId())) {
                this.cityBean.setCityTimeZone("-13");
                this.cityBean.setCityLat(EMPTY);
                this.cityBean.setCityLon(EMPTY);
            }
            //Log.e(TAG, "CITY id" + cityBean.toString());
        }

        @Override
        protected CityBean doInBackground(String... params) {
            return getCityBean(params[0]);
        }

        @Override
        protected void onPostExecute(CityBean cityBeen) {
            if (TextUtils.isEmpty(cityBeen.getCityTimeZone())) {
                mCityBeanList.add(0, cityBeen);
            } else {
                mCityBeanList.add(cityBeen);
            }
            cityNo++;
            mCityText.setText(cityNo + "/" + cityList.length + cityBeen.toString());

            if (mCityBeanList.size() == cityList.length) {
                mHandler.sendEmptyMessage(1);
            }


        }

        private CityBean getCityBean(String param) {
            CityBean cityBeanParser = null;
            try {
                InputStream inputStream = new URL(param).
                        openConnection().getInputStream();
                NewsParser parser = new PullCityBeanParser();
                try {
                    cityBeanParser = parser.parser(inputStream);
                    cityBean.setCityLat(cityBeanParser.getCityLat());
                    cityBean.setCityLon(cityBeanParser.getCityLon());
                    cityBean.setCityTimeZone(cityBeanParser.getCityTimeZone());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return cityBean;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_json) {
            shareText(JSON_FILE_NAME);
            return true;
        }

        if (id == R.id.action_json_city) {
            shareText(JSON_CITY_FILE_NAME);
        }

        return super.onOptionsItemSelected(item);
    }

    //分享文件
    public void shareText(String jsonFileName) {

        File sdCardDir = Environment.getExternalStorageDirectory();
        File targetFile = null;
        try {
            targetFile = new File(sdCardDir.getCanonicalFile(), jsonFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(targetFile));
        shareIntent.setType("*/*");

        //设置分享列表的标题，并且每次都显示分享列表
        startActivity(Intent.createChooser(shareIntent, "通过QQ分享到电脑"));
    }

    private void writeToFile(String stringFile, String fileName) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdCardDir = Environment.getExternalStorageDirectory();
                File targetFile = new File(sdCardDir.getCanonicalFile(), fileName);
                if (targetFile.exists()) {
                    targetFile.delete();
                }
                RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "rw");
                randomAccessFile.seek(targetFile.length());
                randomAccessFile.write(stringFile.getBytes());
                randomAccessFile.close();
                Toast.makeText(getApplicationContext(), "file" + targetFile.getPath() + "create", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            //Log.e(TAG, "File" + e.toString());
            e.printStackTrace();
        }
    }
}
