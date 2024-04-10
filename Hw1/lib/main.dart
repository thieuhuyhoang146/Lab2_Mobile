import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

void main() {
  runApp(SentimentAnalysisApp());
}

class SentimentAnalysisApp extends StatefulWidget {
  @override
  _SentimentAnalysisAppState createState() => _SentimentAnalysisAppState();
}

class _SentimentAnalysisAppState extends State<SentimentAnalysisApp> {
  final TextEditingController _textEditingController = TextEditingController();
  String _sentimentIcon = '😐';
  Color _backgroundColor = Colors.white;

  Future<String> queryAPI(String text) async {
    const apiUrl = "https://api-inference.huggingface.co/models/wonrax/phobert-base-vietnamese-sentiment";
    final headers = {
      "Authorization": "Bearer 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",
      "Content-Type": "application/json",
    };

    final payload = jsonEncode({"inputs": text});
    final response = await http.post(Uri.parse(apiUrl), headers: headers, body: payload);

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body)[0] as List<dynamic>;
      String sentiment = '';
      double maxScore = 0.0;
      for (var item in data) {
        double score = item['score'];
        if (score > maxScore) {
          maxScore = score;
          sentiment = item['label'];
        }
      }
      return sentiment;
    } else {
      throw Exception('Failed to query API');
    }
  }

  void _analyzeSentiment() async {
    String inputText = _textEditingController.text;
    String sentiment;
    try {
      sentiment = await queryAPI(inputText);
    } catch (e) {
      sentiment = 'Error';
    }

    String sentimentIcon;
    Color backgroundColor;
    switch (sentiment) {
      case 'POS':
        sentimentIcon = '😄';
        backgroundColor = const Color.fromARGB(255, 147, 255, 151);
        break;
      case 'NEG':
        sentimentIcon = '😭';
        backgroundColor = Color.fromARGB(255, 239, 126, 126);
        break;
      case 'NEU':
        sentimentIcon = '😐';
        backgroundColor = const Color.fromARGB(255, 255, 255, 255);
        break;
      default:
        sentimentIcon = '😐';
        backgroundColor = const Color.fromARGB(255, 255, 255, 255);
    }

    setState(() {
      _sentimentIcon = sentimentIcon;
      _backgroundColor = backgroundColor;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Phân tích cảm xúc (Homework 1)'),
          backgroundColor: Colors.amber,
        ),
        body: Container(
          color: _backgroundColor,
          child: Column(
              children: [
                const SizedBox(height: 20),
                Text(
                  _sentimentIcon,
                   style: const TextStyle(fontSize: 100),
                ),
                Padding(
                  padding: const EdgeInsets.all(20),
                  child: TextField(
                    controller: _textEditingController,
                    decoration: const InputDecoration(
                      hintText: 'Hãy ghi cảm nghĩ của bạn...',
                      border: OutlineInputBorder(
                        borderSide: BorderSide(
                          color: Colors.blue,
                          width: 10.0,
                        ),
                      )
                  ),
                ),
                ),
                const SizedBox(height: 20),
                ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.amber
                  ),
                  onPressed: _analyzeSentiment,
                  child: const Text('Phân tích'),
                ),
              ],
          ),
        )

      ),
    );
  }
}
