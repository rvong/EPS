require('set')

class TheOne
	def initialize(v)
		@_value = v
	end
	
	def bind(fun)
		@_value = fun.call(@_value)
		return self
	end
	
	def printme
		print @_value
	end
end

def read_file(path)
	return File.read(path)
end

def filter_chars(data)
	return data.gsub(/[\W_]+/, ' ')
end

def normalize(data)
	return data.downcase
end

def scan(data)
	return data.split(' ')
end

def remove_stop_words(word_list)
	stop_words = File.read('../stop_words.txt').split(',').to_set
	return word_list.reject{|w| stop_words.include? w or w.length < 2}
end

def frequencies(word_list)
	freqs = Hash.new(0)
	word_list.each{|w| freqs[w] += 1}
	return freqs
end

def sort(freqs)
	return freqs.sort_by{|x,y| y * -1}
end

def top25(freqs)
	str = ''
	freqs.take(25).each{|w,f| str.concat("#{w} - #{f.to_s}\n")}
	return str
end

TheOne.new(ARGV[0])
	.bind(method(:read_file))
	.bind(method(:filter_chars))
	.bind(method(:normalize))
	.bind(method(:scan))
	.bind(method(:remove_stop_words))
	.bind(method(:frequencies))
	.bind(method(:sort))
	.bind(method(:top25))
	.printme()



