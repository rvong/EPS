require('set')
rec_limit = 5000

def count(word_list, stopwords, wordfreqs)
	return if word_list == []
	word = word_list.first
	wordfreqs[word] += 1 if !stopwords.include? word
	count(word_list.drop(1), stopwords, wordfreqs)
end

def wf_print(wordfreqs)
	return if wordfreqs == []
	(w, f) = wordfreqs.first
	puts "#{w} - #{f}"
	wf_print(wordfreqs.drop(1))
end

# recursive split, the splitting part is easy to do in this style
# if needed, the reading the file recursively part
# would be harder because of the recursion limit and
# having to keep track of the file's cursor position
def rsplit(txt, sack)
	return if txt.length == 0
	pos = txt.index(',')
	return if pos.nil?
	sack.add(txt.slice(0, pos))
	txt[0..pos] = ''
	rsplit(txt, sack)
end

stop_words = Set.new
rsplit(File.read('../stop_words.txt'), stop_words)

words = File.read(ARGV[0]).downcase.scan(/[a-z]{2,}/)
word_freqs = Hash.new(0)

for i in (0..words.size).step(rec_limit)
	count(words.slice(i, rec_limit), stop_words, word_freqs)
end
wf_print(word_freqs.sort_by{|x,y|y*-1}.take(25))
