require('set')

##  Exercise 24.1 + 24.3
##  ===============
##   1) Implement in another language.
##   3) Modify Top25 to print w/o violating constraints.

class TFQuarantine
	def initialize(fun)
		@funs = [fun]
	end
	
	def bind(fun)
		@funs.push(fun)
		return self
	end
	
	def execute
		def guard_callable(v)
            return v.respond_to?('call') ? v.call() : v
		end
		
		value = lambda { nil }
		@funs.each {|f| value = f.call(guard_callable(value)) }
	end
end

def get_input(arg)
	def f
		return ARGV.first
	end
	return f
end

def extract_words(path)
    def f(path)
        return File.read(path).gsub(/[\W_]+/, ' ').downcase.split(' ')
    end
    return f(path)
end

def remove_stop_words(word_list)
    def f(word_list)
        stop_words = File.read('../stop_words.txt').split(',').to_set
        return word_list.reject{|w| stop_words.include? w or w.length < 2}
    end
    return f(word_list)		# (?) Should not call on bind.
end

def frequencies(word_list)
	freqs = Hash.new(0)
	word_list.each{|w| freqs[w] += 1}
	return freqs
end

def sort(freqs)
	return freqs.sort_by{|x,y| y * -1}
end

# (?) Should be higher-order function
def top25(freqs)
    def fun(w, f)
        puts "#{w} - #{f.to_s}"
    end
	freqs.take(25).each{|w,f| fun(w, f)}
end

TFQuarantine.new(method(:get_input))
	.bind(method(:extract_words))
	.bind(method(:remove_stop_words))
	.bind(method(:frequencies))
	.bind(method(:sort))
	.bind(method(:top25))
	.execute()
