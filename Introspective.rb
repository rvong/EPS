require('set')

class EventManager
	def initialize
		@subscriptions = {}
	end
	
	def subscribe(event_type, handler)
		if @subscriptions.has_key? event_type
			@subscriptions[event_type].push(handler)
		else
			@subscriptions[event_type] = [handler]
		end
	end
	
	def unsubscribe(event_type, handler)
		if @subscriptions.has_key? event_type
			@subscriptions[event_type].delete(handler)
		else
			@subscriptions.delete(event_type)
		end
	end
	
	def publish(event)
		event_type = event.first
		if @subscriptions.include? event_type
			@subscriptions[event_type].each {|h| h.call(event)}
		end
	end
end

class DataStorage
	def initialize(event_manager)
		@event_manager = event_manager
		@event_manager.subscribe('load', method(:load))
		@event_manager.subscribe('start', method(:produce_words))
	end
	
	def load(event)
		path_to_file = event[1]
		@data = File.read(path_to_file).downcase
	end
	
	def produce_words(event)
		words = @data.scan(/[a-z]{2,}/)
		words.each{|w| @event_manager.publish(['word', w])}
		@event_manager.publish(['eof', nil])
	end
end

class StopWordFilter
	def initialize(event_manager)
		@stop_words = Set.new
		@event_manager = event_manager
		@event_manager.subscribe('load', method(:load))
		@event_manager.subscribe('word', method(:is_stop_word))
	end
	
	def load(event)
		@stop_words = File.read('../stop_words.txt').split(',').to_set
	end
	
	def is_stop_word(event)
		word = event[1]
		@event_manager.publish(['valid_word', word]) if not @stop_words.include? word
	end
end

class WordFrequencyCounter
	def initialize(event_manager)
		@word_freqs = Hash.new(0)
		@event_manager = event_manager
		@event_manager.subscribe('valid_word', method(:increment_count))
		@event_manager.subscribe('print', method(:print_freqs))
	end	
	
	def increment_count(event)
		word = event[1]
		@word_freqs[word] += 1
	end
	
	def print_freqs(event)
		@word_freqs.sort_by{|x,y|y*-1}.take(25).each{|w,f| puts w+' - '+f.to_s}
	end
end

class WordFrequencyApplication
	def initialize(event_manager)
		@event_manager = event_manager
		@event_manager.subscribe('run', method(:run))
		@event_manager.subscribe('eof', method(:stop))
	end
	
	def run(event)
		path_to_file = event[1]
		@event_manager.publish(['load', path_to_file])
		@event_manager.publish(['start', nil])
	end
	
	def stop(event)
		# test unsubscribe
		@event_manager.unsubscribe('run', method(:run))
		@event_manager.publish(['print', nil])
	end
end

class ZCounter
	def initialize(event_manager)
		@z_count = 0
		@event_manager = event_manager
		@event_manager.subscribe('valid_word', method(:contains_z))
		@event_manager.subscribe('print', method(:print_z))
		@event_manager.subscribe('increment_z', method(:increment_z))
	end	
	
	def contains_z(event)
		word = event[1]
		@event_manager.publish(['increment_z', nil]) if word.include? 'z'
	end
	
	def increment_z(event)
		@z_count += 1
	end
	
	def print_z(event)
		puts "\nNumber of non-stop words with letter z  - #{@z_count}"
	end
end

em = EventManager.new
obj_list = []
obj_list.push(em)
obj_list.push(DataStorage.new(em))
obj_list.push(StopWordFilter.new(em))
obj_list.push(WordFrequencyCounter.new(em))
obj_list.push(ZCounter.new(em))
obj_list.push(WordFrequencyApplication.new(em))
em.publish(['run', ARGV.first])

# unsubscribe works, will not run freq count twice,
#	unless WordFreqApp subscribes to 'run' again
em.publish(['run', ARGV.first])



# 16.3
obj_list.each{
	|x| puts "\nClass Name: #{x.class}"
	print "Methods:"
	x.class.instance_methods(false).each{|y| print  "    ",  y}
	print "\n"
}




