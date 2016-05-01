package main

//
// NOTE: Half baked, or still not baked at all
//
// some initial thoughts...
//
// deamon:
// do
//	api = "https://api.twitter.com/1.1/statuses/home_timeline.json?count=1&since_id=726572231317401602"
// 	get upto 100 tweets since the last $since_id we processed
// 	update $since_id to the last max id we processed from the current batch
// repeat
//
// process(tweet) {
// 	for each tweet parse url
//		figure out the actual url from the shortened url
// 		update to a temp hash[urls]
//	endfor
// 	update the current set of hash[urls] into the already processed {user, urls}
// }
//


import (
	"flag"
	"fmt"
	"log"
	"os"
	//"os/signal"
	//"syscall"

	"github.com/coreos/pkg/flagutil"
	//"github.com/dghubble/go-twitter/twitter"
	"github.com/dghubble/oauth1"
	"io/ioutil"
	"encoding/json"
	"github.com/bitly/go-simplejson"
)

func main() {
	flags := flag.NewFlagSet("user-auth", flag.ExitOnError)
	consumerKey := flags.String("consumer-key", "", "Twitter Consumer Key")
	consumerSecret := flags.String("consumer-secret", "", "Twitter Consumer Secret")
	accessToken := flags.String("access-token", "", "Twitter Access Token")
	accessSecret := flags.String("access-secret", "", "Twitter Access Secret")
	flags.Parse(os.Args[1:])
	flagutil.SetFlagsFromEnv(flags, "TWITTER")

	if *consumerKey == "" || *consumerSecret == "" || *accessToken == "" || *accessSecret == "" {
		log.Fatal("Consumer key/secret and Access token/secret required")
	}

	//fmt.Println(*consumerKey)
	//fmt.Println(*accessToken)

	config := oauth1.NewConfig(*consumerKey, *consumerSecret)
	token := oauth1.NewToken(*accessToken, *accessSecret)
	// OAuth1 http.Client will automatically authorize Requests
	httpClient := config.Client(oauth1.NoContext, token)

	path := "https://api.twitter.com/1.1/statuses/home_timeline.json?count=1&since_id=726572231317401602"
	resp, err := httpClient.Get(path)
	if err != nil {
		panic(err.Error())
	}
	defer resp.Body.Close()
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		panic(err.Error())
	}
	fmt.Printf("Raw Response Body:\n%v\n", string(body))


	//url := body["entities"]["urls"]["display_url"]
	//fmt.Printf("Raw Response Url:\n%v\n", url)

	//byt := []byte(`{"num":6.13,"strs":["a","b"]}`)
	//var dat map[string]interface{}
	//byt := []byte(body)
	val := new(struct {
		Name   string `json:"name"`
		Params *Json  `json:"params"`
	})


	if err := json.Unmarshal([]byte(body), val); err != nil {
		panic(err)
	}
	fmt.Println(val)
	//num := dat["num"].(float64)
	//fmt.Println(num)



	//path := "https://userstream.twitter.com/1.1/user.json"
	//resp, _ := httpClient.Get(path)
	//defer resp.Body.Close()
	//body, _ := ioutil.ReadAll(resp.Body)
	//fmt.Printf("Raw Response Body:\n%v\n", string(body))

}