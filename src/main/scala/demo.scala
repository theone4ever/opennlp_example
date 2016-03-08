/**
  * Created by theone4ever on 08/03/16.
  */

import java.io._

import opennlp.tools.cmdline.PerformanceMonitor
import opennlp.tools.cmdline.postag.POSModelLoader
import opennlp.tools.namefind.{NameFinderME, TokenNameFinderModel}
import opennlp.tools.postag.{POSSample, POSTaggerME}
import opennlp.tools.sentdetect.{SentenceDetectorME, SentenceModel}
import opennlp.tools.tokenize.{WhitespaceTokenizer, TokenizerME, TokenizerModel}
import opennlp.tools.util.PlainTextByLineStream

object demo extends App {


  def sentenceDetect() = {
    val paragraph = "Hi. How are you? This is Mike."

    // always start with a model, a model is learned from training data
    val is = new FileInputStream("models/en-sent.bin")
    val model = new SentenceModel(is)
    val sdetector = new SentenceDetectorME(model)

    val sentences = sdetector.sentDetect(paragraph)

    println(sentences(0))
    println(sentences(1))
    is.close()
  }

  sentenceDetect()

  def tokenize() = {
    val is = new FileInputStream("models/en-token.bin");
    val model = new TokenizerModel(is);
    val tokenizer = new TokenizerME(model);
    tokenizer.tokenize("Hi. How are you? This is Mike.").foreach(a => println(a))
    is.close()
  }

  tokenize()

  def findName() = {
    val is = new FileInputStream("en-ner-person.bin")

    val model = new TokenNameFinderModel(is)
    is.close()

    val nameFinder = new NameFinderME(model)

    val sentence = Array(
      "Mike",
      "Smith",
      "is",
      "a",
      "good",
      "person"
    )

    nameFinder.find(sentence).foreach(s=> println(s.toString))

  }

//  def POSTag()  = {
//    val model = new POSModelLoader()
//      .load(new File("en-pos-maxent.bin"));
//    val perfMon = new PerformanceMonitor(System.err, "sent");
//    val tagger = new POSTaggerME(model);
//     
//    val input = "Hi. How are you? This is Mike.";
//    val lineStream = new PlainTextByLineStream(
//      new StringReader(input));
//     
//    perfMon.start();
//
//    while ((val line = lineStream.read()) != Null) {
//       
//      val whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE
//        .tokenize(line);
//      val tags = tagger.tag(whitespaceTokenizerLine);
//       
//      val sample = new POSSample(whitespaceTokenizerLine, tags);
//      System.out.println(sample.toString());
//       
//      perfMon.incrementCounter();
//    }
//    perfMon.stopAndPrintFinalResult();
//  }
}
