require('set')

def read_file(path, fun)
	data = File.read(path)
	fun.call(data, method(:normalize))
end

def filter_chars(data, fun)
	fun.call(data.gsub(/[\W_]+/, ' '), method(:scan))
end

def normalize(data, fun)
	fun.call(data.downcase, method(:remove_stop_words))
end

def scan(data, fun)
	fun.call(data.split(' '), method(:frequencies))
end

def remove_stop_words(word_list, fun)
	stop_words = File.read('../stop_words.txt').split(',').to_set
	word_list.reject!{|w| stop_words.include? w or w.length < 2}
	fun.call(word_list, method(:sort))
end

def frequencies(word_list, fun)
	freqs = Hash.new(0)
	word_list.each{|w| freqs[w] += 1}
	fun.call(freqs, method(:print_text))
end

def sort(freqs, fun)
	fun.call(freqs.sort_by{|x,y| y * -1}, method(:no_op))
end

def print_text(freqs, fun)
	freqs.take(25).each{|w,f| puts "#{w} - #{f.to_s}"}
	fun.call(nil)
end

def no_op(fun)
	return
end

read_file(ARGV[0], method(:filter_chars))

