package com.flea.crawler.language;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;

import java.util.ArrayList;

/**
 * Created by bilgi on 5/22/2015.
 */
public class FleaLanguageDetectorImpl implements FleaLanguageDetector{

    public static String profileDirectory ="E:\\dev\\lib\\langdetect\\profiles";
    {
        try {
            init(profileDirectory);
        } catch (LangDetectException e) {
            e.printStackTrace();
        }
    }

    public void init(String profileDirectory) throws LangDetectException {
        DetectorFactory.loadProfile(profileDirectory);
    }
    public String detect(String text) throws LangDetectException {
        Detector detector = DetectorFactory.create();
        detector.append(text);
        return detector.detect();
    }
    public ArrayList<Language> detectLangs(String text) throws LangDetectException {
        Detector detector = DetectorFactory.create();
        detector.append(text);
        return detector.getProbabilities();
    }

    public String getHighestProbableLanguage(String text){
        String detectedLang = "unknown";
        try {
            ArrayList<Language> languages = detectLangs(text);
            if(languages.size() > 0){
                detectedLang = languages.get(0).lang;
            }
        } catch (LangDetectException e) {
            e.printStackTrace();
        }
        return detectedLang;
    }

    @Override
    public String detectLanguage(String text) {
        return getHighestProbableLanguage(text);
    }

    public static void main(String[] args){
        FleaLanguageDetectorImpl langDetect = new FleaLanguageDetectorImpl();
        String detected = "unknown";
        try {
            langDetect.init(profileDirectory);
            detected = langDetect.detect("merhabalar nasilsiniz?");
            ArrayList<Language> languages = langDetect.detectLangs("merhabalar nasilsiniz?");
            for(Language language:languages){
                System.out.println("lang: " + language.lang + ": " + language.prob);
            }
        } catch (LangDetectException e) {
            e.printStackTrace();
        }
        System.out.println("langDetect: "+detected);
    }
}
