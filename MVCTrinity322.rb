$limit = 5000

class WordFrequencyModel
  attr_reader :done
  attr_reader :h
  
  def initialize(path)
    @@s = File.read('../stop_words.txt').split(',')
    self.update(path)
  end
  
  def update(path)
    new_file(path)
    @pos, @pos_max = 0, @non_stop.size - 1
    self.count if !done    # new_file sets done if exception
  end
  
  def count
    b = calc_end(@pos)
    (@pos..b).each {|i| @h[@non_stop[i]] += 1}
    @pos += $limit
  end
    
  private
    def new_file(path)
      begin
        @done = false
        @h = Hash.new(0)
        @words = File.read(path).downcase.scan(/[a-z]{2,}/)
        @non_stop = @words.reject{|w| @@s.include? w}
      rescue => err
        @done = true
        puts "Exception: #{err}"
        err
      end
    end

    def calc_end(a)
      b = a + $limit - 1
      if b > @pos_max   # if we go past end set @pos to the end, and set done
        b = @pos_max
        @done = true
      end
      return b
    end
end

class WordFrequencyView
  def initialize(model)
    @model = model
  end
  
  def render
    @model.h.sort_by{|x,y|-y}.take(25)
          .each{|w,f| puts w+' - '+f.to_s}
  end
end

class WordFrequencyController
  def initialize(model, view)
    @model, @view = model, view
  end

  def run
    while true
      @view.render
      
      if @model.done
        self.next_file
      else
        self.prompt_more
      end
    end
  end
  
  def next_file
    print "\n>> Next file: "
    @model.update(get_input)
  end
  
  def prompt_more
    print "\n>> More? [y/n] "
    if get_input == "y"
      @model.count
    else
      self.next_file
    end
  end
  
  private
    def get_input
      STDOUT.flush
      STDIN.gets.chomp
    end
end

m = WordFrequencyModel.new(ARGV.first)  # data
v = WordFrequencyView.new(m)            # get data from model
c = WordFrequencyController.new(m, v)   # talk to model and view

c.run()

