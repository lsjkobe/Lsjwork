package myhttp.parser;

import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-10-08
 * Time: 11:51
 */
public class StringParser implements Parser<String> {
    @Override
    public String parse(Response response) {
        String result=null;
        try {
            result=response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
